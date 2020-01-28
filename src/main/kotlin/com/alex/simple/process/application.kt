package com.alex.simple.process

import com.alex.simple.process.api.DefaultApi
import com.alex.simple.process.api.dto.ModifyProcessDTO
import com.alex.simple.process.api.dto.ProcessDTO
import com.alex.simple.process.api.dto.ProcessDTOList
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*


@SpringBootApplication
@Controller
@EnableTransactionManagement
class Application : DefaultApi {
    override fun create(body: ModifyProcessDTO?): ResponseEntity<ProcessDTO> {
        return ResponseEntity.ok(ProcessDTO())
    }

    override fun update(uuid: UUID, modifyProcessDTO: ModifyProcessDTO?): ResponseEntity<ProcessDTO> {
        return ResponseEntity.ok(ProcessDTO())

    }

    override fun listAll(): ResponseEntity<ProcessDTOList>? {
        return ResponseEntity.ok(ProcessDTOList())
    }

    override fun getById(uuid: UUID): ResponseEntity<ProcessDTO> {
        return ResponseEntity.ok(ProcessDTO())

    }

    override fun delete(uuid: UUID): ResponseEntity<Void> {
        return ResponseEntity.ok(null)
    }

}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}