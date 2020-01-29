package com.alex.simple.process.service

import com.alex.simple.process.domain.Process
import com.alex.simple.process.repository.ProcessRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ProcessService(
    val processRepository: ProcessRepository
) {

    val log = LoggerFactory.getLogger(javaClass)

    fun create(process: Process): Process {
        processRepository.createProcess(process)
        return process
    }
}