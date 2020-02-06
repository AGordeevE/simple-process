package com.alex.simple.process.repository

import com.alex.simple.process.domain.SProcess
import com.alex.simple.process.jooq.tables.JProcesses.PROCESSES
import com.alex.simple.process.service.ContextMapper
import org.jooq.DSLContext
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository

@Repository
class ProcessRepository(
    private val dslContext: DSLContext,
    private val contextMapper: ContextMapper
) {
    val log = LoggerFactory.getLogger(javaClass)

    fun createProcess(process: SProcess) {
        dslContext.insertInto(
            PROCESSES,
            PROCESSES.UUID,
            PROCESSES.PROCESS_TYPE,
            PROCESSES.CONTEXT,
            PROCESSES.RETRY_COUNT
        )
            .values(
                process.uuid,
                process.processType,
                contextMapper.serialize(process.context),
                process.count
            ).execute()
    }

    fun getAll() =
        dslContext
            .selectFrom(PROCESSES)
            .fetch { SProcess(it, contextMapper) }

}