package com.utmaximur.alcoholtracker.ui.dialog.addvolume

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.utmaximur.alcoholtracker.R

class AddVolumeDrinkDialogFragment : DialogFragment() {

    interface SelectVolumeListener {
        fun selectVolumeList(volumeList: List<String>)
    }

    private var selectVolumeListener: SelectVolumeListener? = null

    fun setListener(selectVolumeListener: SelectVolumeListener) {
        this.selectVolumeListener = selectVolumeListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val volume = requireContext().resources.getStringArray(R.array.volume_array)
            val checkedItems = BooleanArray(volume.size)
            val builder = AlertDialog.Builder(it, R.style.AlertDialogVolume)
            builder.setTitle(getText(R.string.dialog_add_volume_title))
                .setMultiChoiceItems(volume, null) { _, which, isChecked ->
                    checkedItems[which] = isChecked
                }
                .setPositiveButton(
                    getText(R.string.dialog_add_volume_ok)
                ) { _, _ ->
                    val volumeList = ArrayList<String>()
                    for (i in volume.indices) {
                        val checked = checkedItems[i]
                        if (checked) {
                            volumeList.add(volume[i])
                        }
                    }
                    selectVolumeListener?.selectVolumeList(volumeList)
                }
                .setNegativeButton( getText(R.string.dialog_add_volume_cancel)) { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}