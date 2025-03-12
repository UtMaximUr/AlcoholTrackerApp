package com.utmaximur.detailTrack

import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.detailTrack.store.DetailTrackStore
import com.utmaximur.geocoder.GeocoderComponent
import kotlinx.coroutines.flow.StateFlow

interface DetailTrackComponent : ComposeComponent {

    val model: StateFlow<DetailTrackStore.State>

    val geocoderComponent: GeocoderComponent

    fun navigateBack()

    fun onSaveClick()

    fun openCalculatorDialog()

    fun openDatePickerDialog(selectedDate: String)

    fun onTodayClick()

    fun onDeleteClick()

    sealed interface Output {

        data object OpenCalculatorDialog : Output

        data class OpenDatePickerDialog(val selectedDate: Long?) : Output

        data class OpenConfirmDialog(val trackId: Long) : Output

        data object NavigateBack : Output
    }
}
