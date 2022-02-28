package com.utmaximur.domain.interactor


import com.utmaximur.domain.entity.Drink
import com.utmaximur.domain.entity.Track
import com.utmaximur.domain.repository.DrinkRepository
import com.utmaximur.domain.repository.PreferencesRepository
import com.utmaximur.domain.repository.TrackRepository
import javax.inject.Inject

class AddTrackInteractor @Inject constructor(
    private val trackRepository: TrackRepository,
    private val drinkRepository: DrinkRepository,
    private val preferencesRepository: PreferencesRepository
) {

    suspend fun insertTrack(track: Track) {
        trackRepository.insertTrack(track)
    }

    suspend fun updateTrack(track: Track) {
        trackRepository.updateTrack(track)
    }

    suspend fun getTrackById(id: String): Track {
        return trackRepository.getTrackById(id)
    }

    suspend fun getDrinks(): List<Drink> {
        return drinkRepository.getDrinks()
    }

    suspend fun deleteDrink(drinkDBO: Drink) {
        drinkRepository.deleteDrink(drinkDBO)
    }

    fun getSaveTheme() = preferencesRepository.getSaveTheme()
}