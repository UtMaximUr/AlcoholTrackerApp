package com.utmaximur.alcoholtracker.presentation.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.databinding.DialogTrackListBinding

import com.utmaximur.alcoholtracker.domain.entity.Track
import com.utmaximur.alcoholtracker.presentation.base.BaseViewModelFactory
import com.utmaximur.alcoholtracker.presentation.calendar.adapter.DrinksListAdapter
import com.utmaximur.alcoholtracker.util.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class TrackListBottomDialog : BottomSheetDialogFragment(),
    DrinksListAdapter.OnDrinkAdapterListener {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory<CalendarViewModel>

    private val viewModel: CalendarViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    private var _binding: DialogTrackListBinding? = null
    private val binding get() = _binding!!

    private var alcoholTrackListAdapter: DrinksListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogTrackListBinding.inflate(inflater)
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
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        initAlcoholTrackByDay()
    }

    private fun initAlcoholTrackByDay() = with(binding) {
        arguments?.getLong(SELECT_DAY)?.let { date -> viewModel.initTracksByDay(date) }

        alcoholTrackListAdapter = DrinksListAdapter(
            this@TrackListBottomDialog
        )
        drinksList.adapter = alcoholTrackListAdapter
        viewModel.tracksByDay.observe(viewLifecycleOwner, { tracks ->
            alcoholTrackListAdapter?.submitList(tracks)
            progressBar.toGone()
            if (tracks.isEmpty()) {
                dismiss()
            }
        })
    }

    override fun onClickEdit(date: Long) {
        val bundle = Bundle()
        lifecycleScope.launch {
            bundle.putParcelable(DRINK, viewModel.dataTrack(date))
            findNavController().navigate(R.id.addFragment, bundle)
        }
    }

    override fun onClickDelete(track: Track, position: Int) {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            KEY_CALENDAR
        )
            ?.observe(
                viewLifecycleOwner
            ) { result ->
                if (result == KEY_CALENDAR_OK) {
                    lifecycleScope.launch {
                        viewModel.onDeleteDrink(track)
                        alcoholTrackListAdapter?.notifyItemRemoved(position)
                        findNavController().getBackStackEntry(R.id.calendarFragment).savedStateHandle.set(
                            KEY_CALENDAR_UPDATE,
                            KEY_CALENDAR_UPDATE_OK
                        )
                    }
                }
            }
        findNavController().navigate(R.id.deleteDialogFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}