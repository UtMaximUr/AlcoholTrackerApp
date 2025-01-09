package com.utmaximur.createTrack

import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.createTrack.store.CreateTrackStore
import com.utmaximur.domain.models.TrackData
import kotlinx.coroutines.flow.StateFlow

interface CreateTrackComponent : ComposeComponent {

    val model: StateFlow<CreateTrackStore.State>

    fun navigateBack()

    fun onSaveClick(trackData: TrackData)

    fun openCalculatorDialog()

    fun openCurrencyDialog()

    fun openDatePickerDialog(selectedDate: String)

    fun onTodayClick()

    fun navigateToCreateDrink()

    sealed interface Output {

        data object OpenCalculatorDialog : Output

        data object OpenCurrencyDialog : Output

        data class OpenDatePickerDialog(val selectedDate: Long?) : Output

        data object NavigateToCreateDrink : Output

        data object NavigateBack : Output
    }
}