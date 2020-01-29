package com.alex.simple.process.domain

import com.alex.simple.process.api.dto.CreateProcessDTO
import com.alex.simple.process.api.dto.ProcessDTO
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

    constructor(dto: ProcessDTO) : this(
        uuid = dto.id ?: UUID.randomUUID(),
        processType = "new",
        context = mapOf<String, String>(),
        count = 5
    )

    constructor(dto: CreateProcessDTO) : this(
        uuid = UUID.randomUUID(),
        processType = "new",
        context = mapOf<String, String>(),
        count = 5
    )
}