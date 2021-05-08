package com.utmaximur.alcoholtracker.ui.dialog.adddrink

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.utmaximur.alcoholtracker.R


class AddDrinkDialogFragment(
    private val addDrinkDialogPositiveClick: () -> Unit,
    private val addDrinkDialogNegativeClick: () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(getText(R.string.dialog_select_date_title))
                .setPositiveButton(getText(R.string.dialog_select_date_ok)) { dialog, _ ->
                    addDrinkDialogPositiveClick()
                    dialog.cancel()
                }
                .setNegativeButton(getText(R.string.dialog_select_date_no)) { dialog, _ ->
                    addDrinkDialogNegativeClick()
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}