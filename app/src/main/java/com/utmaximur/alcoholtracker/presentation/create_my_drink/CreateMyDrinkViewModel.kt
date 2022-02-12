package com.utmaximur.alcoholtracker.presentation.create_my_drink

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.domain.entity.Drink
import com.utmaximur.alcoholtracker.domain.entity.Icon
import com.utmaximur.alcoholtracker.domain.interactor.AddNewDrinkInteractor
import com.utmaximur.alcoholtracker.presentation.create_my_drink.state.EmptyFieldState
import com.utmaximur.alcoholtracker.util.extension.empty
import com.utmaximur.alcoholtracker.util.formatDegree1f
import com.utmaximur.alcoholtracker.util.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CreateMyDrinkViewModel @Inject constructor(
    private var addNewDrinkInteractor: AddNewDrinkInteractor
) : ViewModel() {

    private var id: String = String.empty()
    private var photo: String = String.empty()
    private var name: String = String.empty()
    private var icon: String = String.empty()
    private var degreeList: ArrayList<String?> = ArrayList()
    private var volumeList: ArrayList<String?> = ArrayList()

    val drink: LiveData<Drink> by lazy {
        MutableLiveData()
    }

    val titleFragment: LiveData<Int> by lazy {
        MutableLiveData()
    }

    val emptyFieldState: LiveData<EmptyFieldState> by lazy {
        MutableLiveData()
    }

    fun onSaveButtonClick() {
        if (checkEmptyField()) {
            viewModelScope.launch {
                val drink = Drink(
                    id,
                    name,
                    degreeList,
                    volumeList,
                    icon,
                    photo
                )
                if (id == String.empty()) {
                    val newDrink = drink.copy(id = getDrinkId())
                    addNewDrinkInteractor.insertDrink(newDrink)
                } else {
                    addNewDrinkInteractor.updateDrink(drink)
                }
            }
        }
    }

    fun getIcons(): List<Icon> {
        return addNewDrinkInteractor.getIcons()
    }

    fun getVolumes(context: Context): List<String> {
        val volume = context.resources.getStringArray(R.array.volume_array)
        return volume.toList()
    }

    private fun getDrinkId(): String = UUID.randomUUID().toString()

    private fun checkEmptyField(): Boolean {
        (emptyFieldState as MutableLiveData).value = null
        return when {
            photo.isEmpty () -> {
                emptyFieldState.setValue(EmptyFieldState.Empty(isPhotoEmpty = false))
                false
            }
            name.isEmpty() -> {
                emptyFieldState.setValue(EmptyFieldState.Empty(isNameEmpty = false))
                false
            }
            icon.isEmpty() -> {
                emptyFieldState.setValue(EmptyFieldState.Empty(isIconEmpty = false))
                false
            }
            degreeList.isEmpty() -> {
                emptyFieldState.setValue(EmptyFieldState.Empty(isDegreeEmpty = false))
                false
            }
            volumeList.isEmpty() -> {
                emptyFieldState.setValue(EmptyFieldState.Empty(isVolumeEmpty = false))
                false
            }
            else -> {
                emptyFieldState.setValue(EmptyFieldState.Empty(isNotEmpty = true))
                true
            }
        }
    }

    fun onDrinkChange(drink: Drink?) {

        val dataDrinks = drink
        (this.drink as MutableLiveData).value = dataDrinks

        id = drink?.id.toString()
        photo = drink?.photo.toString()
        name = drink?.drink.toString()
        degreeList = drink?.degree as ArrayList<String?>
        volumeList = drink.volume as ArrayList<String?>
        icon = drink.icon
    }

    fun onPhotoChange(photo: String) {
        this.photo = photo
    }

    fun onNameDrinkChange(name: String) {
        this.name = name
    }

    fun onIconChange(icon: String) {
        this.icon = icon
    }

    fun onDegreeChange(min: Float, max: Float) {
        val size = (max - min).toInt()
        degreeList.clear()
        var doubleMin = min.toDouble()
        for (i in 0 until size * 2) {
            degreeList.add(doubleMin.formatDegree1f())
            doubleMin += 0.5
        }
        if (size == 0) {
            degreeList.add(doubleMin.formatDegree1f())
        }
    }

    fun onVolumeChange(volume: String?) {
        if (volumeList.contains(volume)) {
            volumeList.remove(volume)
        } else {
            volumeList.add(volume)
        }
    }
}