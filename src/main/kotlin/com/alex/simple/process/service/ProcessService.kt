package com.alex.simple.process.service

import com.alex.simple.process.domain.SProcess
import com.alex.simple.process.repository.ProcessRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ProcessService(
    val processRepository: ProcessRepository
) {

    val log = LoggerFactory.getLogger(javaClass)

    fun create(process: SProcess): SProcess {
        processRepository.createProcess(process)
        return process
    }
}