package com.utmaximur.alcoholtracker.ui.calendar.presentation.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.repository.TrackRepository


open class CalendarViewModel(private var trackRepository: TrackRepository) : ViewModel() {

    private var selectDate = 0L

    fun setSelectDate(selectDate: Long) {
         this.selectDate = selectDate
    }

    fun getSelectDate(): Long {
        return selectDate
    }

    fun getTrack(date: Long): LiveData<AlcoholTrack?> {
        return trackRepository.getTrack(date)
    }

    fun getTracks(): LiveData<MutableList<AlcoholTrack>> {
        return trackRepository.getTracks()
    }

    fun deleteDrink(alcoholTrack: AlcoholTrack) {
        trackRepository.deleteTrack(alcoholTrack)
    }
}