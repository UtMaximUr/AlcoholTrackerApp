package com.utmaximur.feature_create_drink

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utmaximur.feature_create_drink.state.EmptyFieldState
import com.utmaximur.utils.empty
import com.utmaximur.utils.formatDegree1f
import com.utmaximur.utils.setValue
import com.utmaximur.domain.entity.Drink
import com.utmaximur.domain.entity.Icon
import com.utmaximur.domain.interactor.AddNewDrinkInteractor
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
    private var degreeList: ArrayList<String> = ArrayList()
    private var volumeList: ArrayList<String> = ArrayList()


    val photoState: LiveData<String> by lazy {
        MutableLiveData()
    }

    val nameState: LiveData<String> by lazy {
        MutableLiveData()
    }

    val iconState: LiveData<String> by lazy {
        MutableLiveData()
    }

    val degreeListState: LiveData<List<String>> by lazy {
        MutableLiveData()
    }

    val volumeListState: LiveData<List<String?>> by lazy {
        MutableLiveData()
    }

    val titleFragment: LiveData<Int> by lazy {
        MutableLiveData()
    }

    val emptyFieldState: LiveData<EmptyFieldState> by lazy {
        MutableLiveData()
    }

    fun onSaveButtonClick(onSuccess: () -> Unit) {
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
            onSuccess()
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
                emptyFieldState.setValue(EmptyFieldState.Empty(isPhotoEmpty = true))
                false
            }
            name.isEmpty() -> {
                emptyFieldState.setValue(EmptyFieldState.Empty(isNameEmpty = true))
                false
            }
            icon.isEmpty() -> {
                emptyFieldState.setValue(EmptyFieldState.Empty(isIconEmpty = true))
                false
            }
            degreeList.isEmpty() -> {
                emptyFieldState.setValue(EmptyFieldState.Empty(isDegreeEmpty = true))
                false
            }
            volumeList.isEmpty() -> {
                emptyFieldState.setValue(EmptyFieldState.Empty(isVolumeEmpty = true))
                false
            }
            else -> true
        }
    }

    fun onDrinkChange(editDinkId: String, title: Int) {
        titleFragment.setValue(title)

        viewModelScope.launch {
            val editDrink = addNewDrinkInteractor.getDrinkById(editDinkId)

            photoState.setValue(editDrink.photo)
            nameState.setValue(editDrink.drink)
            iconState.setValue(editDrink.icon)
            degreeListState.setValue(editDrink.degree)
            volumeListState.setValue(editDrink.volume)

            id = editDrink.id
            photo = editDrink.photo
            name = editDrink.drink
            degreeList = editDrink.degree as ArrayList<String>
            volumeList = editDrink.volume as ArrayList<String>
            icon = editDrink.icon
        }
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

    fun onVolumeChange(volume: String) {
        if (volumeList.contains(volume)) {
            volumeList.remove(volume)
        } else {
            volumeList.add(volume)
        }
        volumeListState.setValue(volumeList)
    }
}