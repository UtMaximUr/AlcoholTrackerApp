package com.utmaximur.workmanager.dsl

import com.utmaximur.workmanager.SimpleWorkManager
import com.utmaximur.workmanager.logger.DefaultLogger
import com.utmaximur.workmanager.logger.Logger
import com.utmaximur.workmanager.registerPlatform
import com.utmaximur.workmanager.work.Worker

/**
 * Dsl method to initialize [SimpleWorkManager]
 */
fun simpleWorkMangerInitializer(block: WorkerDefinition.() -> Unit) {
    //TODO it's for ios only now, but need will to remade
    registerPlatform()
    val workerDefinition = WorkerDefinition().apply(block)
    SimpleWorkManager.initialize(workerDefinition)
}


class WorkerDefinition internal constructor() {

    internal val definitions = mutableMapOf<String, Worker>()
    internal var loggerDefinition: LoggerDefinition? = null
    fun work(worker: Worker, identifier: String = worker.identifier) {
        loggerDefinition?.logger?.log(message = "Work with simple name = ${worker.identifier}")
        definitions[identifier] = worker
    }

    fun logger(block: LoggerDefinition.() -> Unit) {
        loggerDefinition = LoggerDefinition().apply(block)
    }

}

class LoggerDefinition internal constructor() {
    var enable: Boolean = false
    var logger: Logger = DefaultLogger
}
