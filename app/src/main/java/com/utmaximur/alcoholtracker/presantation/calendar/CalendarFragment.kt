package com.utmaximur.alcoholtracker.presantation.calendar

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import com.applandeo.materialcalendarview.EventDay
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.databinding.FragmentCalendarBinding
import com.utmaximur.alcoholtracker.domain.entity.TrackCalendar
import com.utmaximur.alcoholtracker.presantation.base.BaseViewModelFactory
import com.utmaximur.alcoholtracker.presantation.calendar.adapter.DrinksListAdapter
import com.utmaximur.alcoholtracker.util.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


class CalendarFragment : Fragment(),
    DrinksListAdapter.OnDrinkAdapterListener {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory<CalendarViewModel>

    private val viewModel: CalendarViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    private var calendarFragmentListener: CalendarFragmentListener? = null

    interface CalendarFragmentListener {
        fun showEditAlcoholTrackerFragment(bundle: Bundle)
        fun showAddAlcoholTrackerFragment(bundle: Bundle?)
    }

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private var alcoholTrackListAdapter: DrinksListAdapter? = null


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
    }

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    private fun initUI() {
        viewModel.initTracks()
        binding.addButton.setOnClickListener {
            if (viewModel.getSelectDate() != 0L) {
                val navController = findNavController()
                navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
                    KEY_CALENDAR_DATA
                )?.observe(
                    viewLifecycleOwner
                ) { result ->
                    if (result == KEY_CALENDAR_DATA_OK) {
                        val bundle = Bundle()
                        bundle.putLong(SELECT_DAY, viewModel.getSelectDate())
                        calendarFragmentListener?.showAddAlcoholTrackerFragment(bundle)
                    } else {
                        calendarFragmentListener?.showAddAlcoholTrackerFragment(null)
                    }
                }
                navController.navigate(R.id.addDrinkDialogFragment)
            } else {
                calendarFragmentListener?.showAddAlcoholTrackerFragment(null)
            }
        }

        binding.openDrinkList.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                changeConstraints(ConstraintSet.TOP)
            } else {
                changeConstraints(ConstraintSet.BOTTOM)
            }
        }

        binding.calendarView.setOnDayClickListener { eventDay ->
            getAlcoholTrackByDay(eventDay.calendar.timeInMillis)
            viewModel.setSelectDate(eventDay.calendar.timeInMillis)
        }
        initCalendar()
    }

    private fun changeConstraints(constraintSet: Int) {
        val set = ConstraintSet()
        set.clone(binding.calendarFragment)
        set.connect(
            binding.parentDrinkList.id,
            ConstraintSet.TOP,
            binding.calendarView.id,
            constraintSet
        )
        TransitionManager.beginDelayedTransition(binding.calendarFragment)
        set.applyTo(binding.calendarFragment)
    }

    private fun initCalendar() {
        // добавление иконок алкоголя в календарь
        setIconOnDate()
        getAlcoholTrackByDay(Date().time)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        calendarFragmentListener = context as CalendarFragmentListener
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.getSelectDate() != 0L) {
            viewModel.setSelectDate(0L)
        }
    }

    override fun onEdit(date: Long) {
        val bundle = Bundle()
        lifecycleScope.launch {
            bundle.putParcelable(DRINK, viewModel.getTrack(date))
            calendarFragmentListener?.showEditAlcoholTrackerFragment(bundle)
        }
    }

    override fun onDelete(track: TrackCalendar, position: Int) {
        val navController = findNavController()
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>(KEY_CALENDAR)
            ?.observe(
                viewLifecycleOwner
            ) { result ->
                if (result == KEY_CALENDAR_OK) {
                    lifecycleScope.launch {
                        viewModel.deleteDrink(track)
                    }
                    alcoholTrackListAdapter?.notifyItemRemoved(position)
                    setIconOnDate()
                }
            }
        navController.navigate(R.id.deleteDialogFragment)
    }

    private fun getAlcoholTrackByDay(eventDay: Long) {

        alcoholTrackListAdapter = DrinksListAdapter(
            this@CalendarFragment
        )
        binding.drinksList.adapter = alcoholTrackListAdapter
        lifecycleScope.launch {
            val tracks = viewModel.getAlcoholTrackByDay(eventDay)
            alcoholTrackListAdapter?.submitList(tracks)
//            alcoholTrackListAdapter?.notifyDataSetChanged()

            if (tracks.isNotEmpty()) {
                binding.emptyDrinkList.toGone()
            } else {
                if (tracks.isEmpty()) {
                    binding.addToStart.toVisible()
                } else {
                    binding.emptyDrinkList.toVisible()
                }
            }
            binding.progressBar.toGone()
        }
    }

    private fun setIconOnDate() {
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
            binding.calendarView.setEvents(events)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}