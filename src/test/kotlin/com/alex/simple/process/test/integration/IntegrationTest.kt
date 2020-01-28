package com.alex.simple.process.test.integration

import com.alex.simple.process.test.utils.TestUtil.Utils.initDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class IntegrationTest(
) {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun contextStarted() {
    }

    @Test
    fun exampleTest() {
        val exampleDto = initDto("/test_scripts/create_dto.json", ExampleDto::class.java, objectMapper)
        assertEquals(0, exampleDto?.id)
        assertEquals("test_name", exampleDto?.name)
    }

    data class ExampleDto(val id: Int, val name: String)

}