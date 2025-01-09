package com.utmaximur.core.decompose

interface ComposeComponentProducer<OUTPUT, EVENT> :
    EventProducer<EVENT>,
    OutputProducer<OUTPUT>,
    ComposeComponent

interface EventProducer<EVENT> {
    fun onEvent(event: EVENT)
}

interface OutputProducer<OUTPUT> {
    fun onOutput(output: OUTPUT)
}