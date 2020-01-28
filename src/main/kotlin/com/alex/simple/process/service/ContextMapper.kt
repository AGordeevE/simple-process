package com.alex.simple.process.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue


import org.jooq.JSONB
import org.springframework.stereotype.Service


@Service
class ContextMapper {

    val mapper = jacksonObjectMapper()

    fun serialize(value: Map<String, String>): JSONB =
        JSONB.valueOf(mapper.writeValueAsString(value))

    fun deserialize(content: JSONB): Map<String, String> =
        mapper.readValue(content.toString())
}