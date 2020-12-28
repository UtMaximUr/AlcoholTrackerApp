package com.utmaximur.alcoholtracker.repository

import androidx.lifecycle.LiveData
import com.utmaximur.alcoholtracker.data.AlcoholTrackDatabase
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.dao.AlcoholTrackDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TrackRepository(alcoholTrackDatabase: AlcoholTrackDatabase) {

    private var trackDao: AlcoholTrackDao = alcoholTrackDatabase.getTrackDao()

    fun getTracks(): LiveData<MutableList<AlcoholTrack>> {
        return trackDao.getTracks()
    }

    fun getTrack(date: Long): LiveData<AlcoholTrack?> {
        return trackDao.getTrack(date)
    }

    fun insertTrack(track: AlcoholTrack) {
        CoroutineScope(Dispatchers.IO).launch {
            trackDao.insertTrack(track)
        }
    }

    fun deleteTrack(track: AlcoholTrack) {
        CoroutineScope(Dispatchers.IO).launch {
            trackDao.deleteTrack(track)
        }
    }

    fun updateTrack(track: AlcoholTrack) {
        CoroutineScope(Dispatchers.IO).launch {
            trackDao.updateTrack(track)
        }
    }
}