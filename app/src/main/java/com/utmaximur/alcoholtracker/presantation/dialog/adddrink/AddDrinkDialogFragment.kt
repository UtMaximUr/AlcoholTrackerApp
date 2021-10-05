package com.utmaximur.alcoholtracker.presantation.dialog.adddrink

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.util.*


class AddDrinkDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(getText(R.string.dialog_select_date_title))
                .setPositiveButton(getText(R.string.dialog_select_date_ok)) { dialog, _ ->
                    findNavController().previousBackStackEntry?.savedStateHandle?.set(
                        KEY_CALENDAR_DATA, KEY_CALENDAR_DATA_OK
                    )
                    findNavController().previousBackStackEntry?.savedStateHandle?.remove<String>(KEY_CALENDAR_DATA)
                    dialog.cancel()
                }
                .setNegativeButton(getText(R.string.dialog_select_date_no)) { dialog, _ ->
                    findNavController().previousBackStackEntry?.savedStateHandle?.set(
                        KEY_CALENDAR_DATA, KEY_CALENDAR_DATA_NO
                    )
                    findNavController().previousBackStackEntry?.savedStateHandle?.remove<String>(KEY_CALENDAR_DATA)
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}