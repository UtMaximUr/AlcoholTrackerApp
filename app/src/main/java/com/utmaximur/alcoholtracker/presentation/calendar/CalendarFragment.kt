package com.utmaximur.alcoholtracker.presentation.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgument
import androidx.navigation.fragment.findNavController
import com.applandeo.materialcalendarview.EventDay
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.databinding.FragmentCalendarBinding
import com.utmaximur.alcoholtracker.presentation.base.BaseViewModelFactory
import com.utmaximur.alcoholtracker.util.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


class CalendarFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory<CalendarViewModel>

    private val viewModel: CalendarViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDagger()
        initUI()
        initIconOnDate()
        onEventDayChange(null)
        updateCalendar()
    }

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    private fun initUI() = with(binding) {
        calendarView.setOnDayClickListener { eventDay -> onClickCalendar(eventDay) }
    }

    private fun updateCalendar() {
        this.getNavigationResultLiveData<String>(KEY_CALENDAR_UPDATE)
            ?.observe(viewLifecycleOwner) { viewModel.updateTracks() }
    }

    private fun onClickCalendar(eventDay: EventDay) {
        lifecycleScope.launch {
            val trackByDay = viewModel.dataAlcoholTrackByDay(eventDay.calendar.timeInMillis)
            if (trackByDay.isNotEmpty()) {
                val bundle = Bundle()
                bundle.putLong(SELECT_DAY, eventDay.calendar.timeInMillis)
                findNavController().navigate(R.id.trackListBottomDialogFragment, bundle)
            } else {
                val argument =
                    NavArgument.Builder().setDefaultValue(eventDay.calendar.timeInMillis).build()
                findNavController().graph.findNode(R.id.addFragment)
                    ?.addArgument(SELECT_DAY_ADD, argument)
            }
            onEventDayChange(eventDay.calendar.timeInMillis)
        }
    }

    private fun initIconOnDate() = with(binding) {
        val events: MutableList<EventDay> = ArrayList()
        viewModel.tracks.observe(viewLifecycleOwner, { list ->
            events.clear()
            list.forEach {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = it.date
                events.add(
                    EventDay(
                        calendar,
                        it.icon.getIdRaw(requireContext())
                    )
                )
            }
            calendarView.setEvents(events)
        })
    }

    private fun onEventDayChange(eventDay: Long?) = with(binding) {
        lifecycleScope.launch {
            val trackByDay = viewModel.dataAlcoholTrackByDay(eventDay ?: Date().time)
            viewModel.tracks.observe(viewLifecycleOwner, { list ->
                if (list.isEmpty()) {
                    addToStart.toVisible()
                    selectDate.toGone()
                } else {
                    if (trackByDay.isNotEmpty()) {
                        emptyDrinkList.toGone()
                        selectDate.toVisible()
                    } else {
                        selectDate.toGone()
                        emptyDrinkList.toVisible()
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}