package com.utmaximur.alcoholtracker.ui.dialog.delete

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.utmaximur.alcoholtracker.R


class DeleteDialogFragment(private val deleteClick: () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle(getText(R.string.dialog_delete_title))
                .setPositiveButton(getText(R.string.dialog_select_date_ok)) { dialog, _ ->
                    Log.d("fix_delete", "DeleteDialogFragment - OK")
                    deleteClick()
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