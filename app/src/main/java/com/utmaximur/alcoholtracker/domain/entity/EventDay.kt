package com.utmaximur.alcoholtracker.domain.entity

import com.utmaximur.alcoholtracker.util.extension.empty


data class EventDay(val date: Long) {
    internal var idDrawable: String = String.empty()

    constructor(date: Long, idDrawable: String) : this(date) {
        this.idDrawable = idDrawable
    }
}
