package com.alex.simple.process

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.transaction.annotation.EnableTransactionManagement


@SpringBootApplication
@EnableTransactionManagement
class Application

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}