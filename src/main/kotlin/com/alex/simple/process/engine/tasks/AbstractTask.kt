package com.alex.simple.process.engine.tasks

abstract class AbstractTask {

    abstract fun process(context: Map<String, String>): String
}