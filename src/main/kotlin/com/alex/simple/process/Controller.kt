package com.alex.simple.process

import com.alex.simple.process.api.DefaultApi
import com.alex.simple.process.api.dto.*
import com.alex.simple.process.domain.Process
import com.alex.simple.process.service.ProcessService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class Controller(
    val processService: ProcessService
) : DefaultApi {
    override fun create(body: CreateProcessDTO): ResponseEntity<ProcessDTO> {
        val process = processService.create(Process(body))

        return ResponseEntity.ok(ProcessDTO().id(process.uuid))
    }

    override fun update(uuid: UUID, modifyProcessDTO: ModifyProcessDTO?): ResponseEntity<ProcessDTO> {
        return ResponseEntity.ok(ProcessDTO())
    }

    override fun listAll(): ResponseEntity<ProcessDTOList>? {
        return ResponseEntity.ok(ProcessDTOList())
    }

    override fun getById(uuid: UUID): ResponseEntity<ProcessDTO> {
        val processDTO = ProcessDTO()
            .id(uuid)
            .code("test")
            .context("context")
            .state(ProcessState.NEW)
        return ResponseEntity.ok(processDTO)
    }

    override fun delete(uuid: UUID): ResponseEntity<Void> {
        return ResponseEntity.noContent().build()
    }
}