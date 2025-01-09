package com.utmaximur.datePicker

import com.utmaximur.core.decompose.ComposeDialogComponent
import com.utmaximur.datePicker.store.DatePickerStore
import kotlinx.coroutines.flow.StateFlow

interface DatePickerComponent : ComposeDialogComponent {

    val model: StateFlow<DatePickerStore.State>

    fun handleSelectDate(date: Long?)
}