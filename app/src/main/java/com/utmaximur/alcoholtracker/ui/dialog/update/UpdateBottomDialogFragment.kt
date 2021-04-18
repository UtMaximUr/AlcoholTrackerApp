package com.utmaximur.alcoholtracker.ui.dialog.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.update.UpdateManager

class UpdateBottomDialogFragment : BottomSheetDialogFragment() {

    private lateinit var restartButton: Button
    private lateinit var laterButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(
            R.layout.dialog_update_bottom_sheet, container,
            false
        )
        initUi(view)
        return view
    }

    private fun findViewById(view: View) {
        restartButton = view.findViewById(R.id.restart_button)
        laterButton = view.findViewById(R.id.later_button)
    }

    private fun initUi(view: View) {
        findViewById(view)
        restartButton.setOnClickListener {
            UpdateManager.getInstance().completeUpdate()
        }
        laterButton.setOnClickListener {
            dismiss()
        }
    }
}