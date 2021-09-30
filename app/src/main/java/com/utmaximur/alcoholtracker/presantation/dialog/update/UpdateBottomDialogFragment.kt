package com.utmaximur.alcoholtracker.presantation.dialog.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.utmaximur.alcoholtracker.data.update.UpdateManager
import com.utmaximur.alcoholtracker.databinding.DialogUpdateBottomSheetBinding

class UpdateBottomDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: DialogUpdateBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogUpdateBottomSheetBinding.inflate(inflater)
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