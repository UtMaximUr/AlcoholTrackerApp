package com.utmaximur.datePicker.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.datePicker.store.DatePickerStore.Intent
import com.utmaximur.datePicker.store.DatePickerStore.Label
import com.utmaximur.datePicker.store.DatePickerStore.State

interface DatePickerStore : Store<Intent, State, Label> {

    data class State(
        val selectedDate: Long?
    ) {
        constructor() : this(
            selectedDate = null
        )
    }

    sealed interface Intent {

        data class SelectedDate(val date: Long?) : Intent
    }

    sealed interface Label {

        data object CloseEvent : Label

    }
}