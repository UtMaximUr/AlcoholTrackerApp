package com.utmaximur.domain.entity


data class EventDay(val date: Long) {
    var idDrawable: String = ""

    constructor(date: Long, idDrawable: String) : this(date) {
        this.idDrawable = idDrawable
    }
}
