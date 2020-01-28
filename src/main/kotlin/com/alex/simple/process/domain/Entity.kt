package com.alex.simple.process.domain

import com.alex.simple.process.jooq.tables.pojos.JProcesses
import com.alex.simple.process.service.ContextMapper
import java.util.*

data class Process(
    val uuid: UUID,
    val processType: String,
    val context: Map<String, String>,
    val count: Int
) {
    constructor(record: JProcesses, mapper: ContextMapper) : this(
        uuid = record.uuid,
        processType = record.processType,
        context = mapper.deserialize(record.context),
        count = record.retryCount
    )
}