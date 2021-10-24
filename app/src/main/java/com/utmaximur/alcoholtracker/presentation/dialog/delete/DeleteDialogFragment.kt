package com.utmaximur.alcoholtracker.presentation.dialog.delete

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.util.*


class DeleteDialogFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(getText(R.string.dialog_delete_title))
                .setPositiveButton(getText(R.string.dialog_select_date_ok)) { dialog, _ ->
                    this@DeleteDialogFragment.setNavigationResult(KEY_CALENDAR, KEY_CALENDAR_OK, true)
                    this@DeleteDialogFragment.setNavigationResult(KEY_ADD, KEY_ADD_OK, true)
                    dialog.cancel()
                }
                .setNegativeButton(getText(R.string.dialog_select_date_no)) {
                        dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}