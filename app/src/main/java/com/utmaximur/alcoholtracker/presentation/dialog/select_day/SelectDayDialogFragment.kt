package com.utmaximur.alcoholtracker.presentation.dialog.select_day

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.util.*


class SelectDayDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(getText(R.string.dialog_select_date_title))
                .setPositiveButton(getText(R.string.dialog_select_date_ok)) { dialog, _ ->
                    this.setNavigationResult(KEY_CALENDAR_DATA, KEY_CALENDAR_DATA_OK, true)
                    dialog.cancel()
                }
                .setNegativeButton(getText(R.string.dialog_select_date_no)) { dialog, _ ->
                    this.setNavigationResult(KEY_CALENDAR_DATA, KEY_CALENDAR_DATA_NO, true)
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}