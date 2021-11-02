package com.utmaximur.alcoholtracker.presentation.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.databinding.FragmentDataBinding
import com.utmaximur.alcoholtracker.presentation.base.BaseViewModelFactory
import javax.inject.Inject


class DataFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory<DataViewModel>

    private val viewModel: DataViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    private var _binding: FragmentDataBinding? = null
    private val binding get() = _binding!!

    private var dataFragmentListener:DataFragmentListener? = null

    interface DataFragmentListener {
        fun onHideNavigationBar()
        fun onShowNavigationBar()
        fun closeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDataBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDagger()
        initUi()
    }

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    private fun initUi() = with(binding) {
        toolbar.setNavigationOnClickListener {
           dataFragmentListener?.closeFragment()
        }
        backupButton.setOnClickListener { viewModel.onBackupClick() }
        restoreButton.setOnClickListener { viewModel.onRestoreClick() }
        deleteButton.setOnClickListener { viewModel.onDeleteClick() }
        passwordButton.setOnClickListener {  }
        deletePasswordButton.setOnClickListener {  }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataFragmentListener = context as DataFragmentListener
    }

    override fun onStart() {
        super.onStart()
        dataFragmentListener?.onHideNavigationBar()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dataFragmentListener?.onShowNavigationBar()
        _binding = null
    }
}
