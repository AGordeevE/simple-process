package com.alex.simple.process.engine

import com.alex.simple.process.domain.SProcess
import com.alex.simple.process.engine.tasks.AbstractTask
import com.alex.simple.process.repository.ProcessRepository
import org.springframework.context.support.AbstractApplicationContext
import org.springframework.stereotype.Service

@Service
class Processor(
    private val processRepository: ProcessRepository,
    private val applicationContext: AbstractApplicationContext
) {

    fun processing() {
        val processes = processRepository.getAll()


    }

    fun execute(sProcess: SProcess) {
        val taskName = sProcess.processType

        val taskBean: AbstractTask = applicationContext.getBean(taskName) as AbstractTask


    }
}