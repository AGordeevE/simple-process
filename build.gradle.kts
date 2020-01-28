import org.apache.tools.ant.filters.ReplaceTokens
import org.gradle.process.internal.ExecException
import org.hidetake.gradle.swagger.generator.GenerateSwaggerCode
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.*
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.Target
import java.io.ByteArrayOutputStream

buildscript {
    val versionJooq: String by project
    val versionPostgresql: String by project

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jooq:jooq-codegen:$versionJooq")
        classpath("org.postgresql:postgresql:$versionPostgresql")
    }
}

plugins {
    val versionKotlin: String by project
    val versionSpringBoot: String by project
    val versionDependencyManagement: String by project
    val versionJib: String by project

    kotlin("jvm") version versionKotlin
    kotlin("plugin.spring") version versionKotlin
    id("org.springframework.boot") version versionSpringBoot
    id("io.spring.dependency-management") version versionDependencyManagement
    id("com.google.cloud.tools.jib") version versionJib
    id("org.hidetake.swagger.generator") version "2.18.1"
}

fun getCommitHash(): String {
    val stdout = ByteArrayOutputStream()
    try {
        exec {
            commandLine("git", "rev-parse", "--short", "HEAD")
            standardOutput = stdout
        }
    } catch (ignored: ExecException) {
        return "unknown"
    }
    return stdout.toString().trim()
}

val projectName: String = rootProject.name
val servicePackage = projectName.replaceFirst("-", ".").replace('-', '_')
val substitutionTokens by lazy {
    properties
        .map { "project.${it.key}" to (it.value?.toString() ?: "unknown") }
        .toMap()
}
val openapiPattern = "openapi/*.yaml"

group = "com.alex.process.kotlin.$projectName"
version = getCommitHash()

val versionKotlin: String by project
val versionSpringBoot: String by project
val versionSpringCloud: String by project
val versionSpringCloudStream: String by project
val versionDependencyManagement: String by project
val versionJib: String by project
val versionJooq: String by project
val versionPostgresql: String by project

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$versionSpringCloud")
        mavenBom("org.springframework.cloud:spring-cloud-stream-dependencies:$versionSpringCloudStream")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))


    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-jooq")

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.cloud:spring-cloud-starter-oauth2")
    implementation("org.springframework.security.oauth.boot:spring-security-oauth2-autoconfigure:$versionSpringBoot")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.postgresql:postgresql:$versionPostgresql")
    implementation("org.jooq:jooq:$versionJooq")

    implementation("io.swagger:swagger-annotations:1.5.22")
    implementation("net.logstash.logback:logstash-logback-encoder:4.11")
    implementation("org.codehaus.janino:janino:3.0.6")

    implementation("io.springfox:springfox-swagger2:2.9.2")
    implementation("io.springfox:springfox-swagger-ui:2.9.2")

    testRuntime("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "junit", module = "junit")
    }
    testImplementation("org.testcontainers:postgresql:1.11.4")

    swaggerCodegen("org.openapitools:openapi-generator-cli:3.3.4")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

val processResources: ProcessResources by tasks
processResources.exclude(openapiPattern)
processResources.filter<ReplaceTokens>("tokens" to substitutionTokens)

val processTestResources: ProcessResources by tasks
processTestResources.filter<ReplaceTokens>("tokens" to substitutionTokens)

val test: Test by tasks
test.useJUnitPlatform()
test.reports.junitXml.isEnabled = true
test.reports.html.isEnabled = true

jib {
    from {
        image = "openjdk:8-jre-alpine"
    }
    container {
        jvmFlags = listOf("-server", "-Xmx64m", "-Xss512k", "-Djava.security.egd=file:/dev/./urandom")
        ports = listOf("8080")
    }
}

sourceSets["main"].resources.asFileTree.matching {
    include(openapiPattern)
}.forEach {
    val fileName = it.nameWithoutExtension
    val apiPackage = it.nameWithoutExtension.replace('-', '_')

    val processSwagger by tasks.register<ProcessResources>("processSwagger_$fileName") {
        from(it)
        filter<ReplaceTokens>("tokens" to substitutionTokens)
        into(processResources.destinationDir)
    }

    val generateSwagger by tasks.register<GenerateSwaggerCode>("generateSwagger_$fileName") {
        dependsOn(processSwagger)
        inputFile = processSwagger.destinationDir.resolve(it.name)
        outputDir = file("$buildDir/generated/$fileName")
        language = "spring"
        library = "spring-cloud"
        wipeOutputDir = true
        additionalProperties = mapOf(
            "apiPackage" to "com.alex.$servicePackage.$apiPackage",
            "modelPackage" to "com.alex.$servicePackage.$apiPackage.dto",
            "configPackage" to "com.alex.$servicePackage.$apiPackage.configuration"
        )
    }

    tasks.named<KotlinCompile>("compileKotlin") {
        dependsOn(generateSwagger)
        sourceSets["main"].java.srcDir("${generateSwagger.outputDir}/src/main/java")
        sourceSets["main"].resources.srcDir("${generateSwagger.outputDir}/src/main/resources")
    }
}

val jooq by tasks.creating {
    val configuration = Configuration()

    configuration.apply {
        logging = Logging.WARN

        jdbc = Jdbc().apply {
            driver = "org.postgresql.Driver"
            url = "jdbc:postgresql://localhost:5432/process?user=postgres&password=123456"
        }

        generator = Generator().apply {
            strategy = Strategy().apply {
                name = "org.jooq.codegen.example.JPrefixGeneratorStrategy"
            }
            database = Database().apply {
                includes = listOf("processes").joinToString("|")
                name = "org.jooq.meta.postgres.PostgresDatabase"
                schemata = listOf(
                    SchemaMappingType().apply {
                        inputSchema = "public"
                    }
                )
            }
            generate = Generate().apply {
                isPojos = true
                isDaos = false
            }
            target = Target().apply {
                packageName = "com.alex.$servicePackage.jooq"
                directory = "$projectDir/src/main/java"
            }
        }
    }

    sourceSets["main"].java.srcDir(configuration.generator.target.directory)

    doLast {
        GenerationTool.generate(configuration)
    }
}
