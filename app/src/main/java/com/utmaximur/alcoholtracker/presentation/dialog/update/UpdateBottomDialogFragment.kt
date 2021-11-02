package com.utmaximur.alcoholtracker.presentation.dialog.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.utmaximur.alcoholtracker.data.update.UpdateManager
import com.utmaximur.alcoholtracker.databinding.DialogUpdateBinding

class UpdateBottomDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: DialogUpdateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogUpdateBinding.inflate(inflater)
        initUi()
        return binding.root
    }

    private fun initUi() {
        binding.restartButton.setOnClickListener {
            UpdateManager.getInstance().completeUpdate()
        }
        binding.laterButton.setOnClickListener {
            dismiss()
        }
    }
}