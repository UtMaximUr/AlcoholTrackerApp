package com.utmaximur.alcoholtracker.domain.interactor

import com.utmaximur.alcoholtracker.data.repository.DrinkRepository
import com.utmaximur.alcoholtracker.data.repository.TrackRepository
import com.utmaximur.alcoholtracker.domain.entity.Drink
import com.utmaximur.alcoholtracker.domain.entity.Track
import javax.inject.Inject

class AddTrackInteractor @Inject constructor(
    private val trackRepository: TrackRepository,
    private val drinkRepository: DrinkRepository
) {

    suspend fun insertTrack(track: Track) {
        trackRepository.insertTrack(track)
    }

    suspend fun updateTrack(track: Track) {
        trackRepository.updateTrack(track)
    }

    suspend fun getTrackById(id: String) : Track {
        return trackRepository.getTrackById(id)
    }

    suspend fun getDrinks(): List<Drink> {
        return drinkRepository.getDrinks()
    }

    suspend fun deleteDrink(drinkDBO: Drink) {
        drinkRepository.deleteDrink(drinkDBO)
    }
}