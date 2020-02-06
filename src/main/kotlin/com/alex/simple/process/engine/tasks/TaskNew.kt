package com.alex.simple.process.engine.tasks

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service(value = "new")
class TaskNew : AbstractTask() {

    val log = LoggerFactory.getLogger(javaClass)

    override fun process(context: Map<String, String>): String {
        log.info("process task.")
        return "first"
    }
}