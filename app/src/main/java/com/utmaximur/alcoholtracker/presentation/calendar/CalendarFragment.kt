package com.utmaximur.alcoholtracker.presentation.calendar

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
import com.utmaximur.alcoholtracker.domain.entity.Track
import com.utmaximur.alcoholtracker.presentation.base.BaseViewModelFactory
import com.utmaximur.alcoholtracker.presentation.calendar.adapter.DrinksListAdapter
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
        initCalendar()
    }

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    private fun initUI() = with(binding) {
        addButton.setOnClickListener { onClickAddTrack() }
        openDrinkList.setOnCheckedChangeListener { _, checked -> onClickShowDrinks(checked) }
        calendarView.setOnDayClickListener { eventDay -> onClickCalendar(eventDay) }
        viewModel.tracks.observe(viewLifecycleOwner, { tracks ->
            if (tracks.isNotEmpty()) {
                emptyDrinkList.toVisible()
            } else {
                addToStart.toGone()
                emptyDrinkList.toVisible()
            }
        })
    }

    private fun onClickAddTrack() = with(binding) {
        if (isDateEqual(calendarView.firstSelectedDate.timeInMillis, requireContext())) {
            findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
                KEY_CALENDAR_DATA
            )?.observe(
                viewLifecycleOwner
            ) { result ->
                if (result == KEY_CALENDAR_DATA_OK) {
                    val bundle = Bundle()
                    bundle.putLong(SELECT_DAY, calendarView.firstSelectedDate.timeInMillis)
                    calendarFragmentListener?.showAddAlcoholTrackerFragment(bundle)
                } else {
                    calendarFragmentListener?.showAddAlcoholTrackerFragment(null)
                }
            }
            findNavController().navigate(R.id.addDrinkDialogFragment)
        } else {
            calendarFragmentListener?.showAddAlcoholTrackerFragment(null)
        }
    }

    private fun onClickShowDrinks(checked: Boolean) {
        if (checked) {
            changeConstraints(ConstraintSet.TOP)
        } else {
            changeConstraints(ConstraintSet.BOTTOM)
        }
    }

    private fun onClickCalendar(eventDay: EventDay) {
        initAlcoholTrackByDay(eventDay.calendar.timeInMillis)
    }

    private fun changeConstraints(constraintSet: Int) = with(binding) {
        val set = ConstraintSet()
        set.clone(calendarFragment)
        set.connect(
            parentDrinkList.id,
            ConstraintSet.TOP,
            calendarView.id,
            constraintSet
        )
        TransitionManager.beginDelayedTransition(calendarFragment)
        set.applyTo(calendarFragment)
    }

    private fun initCalendar() {
        initIconOnDate()
        initAlcoholTrackByDay(Date().time)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        calendarFragmentListener = context as CalendarFragmentListener
    }

    override fun onClickEdit(date: Long) {
        val bundle = Bundle()
        lifecycleScope.launch {
            bundle.putParcelable(DRINK, viewModel.dataTrack(date))
            calendarFragmentListener?.showEditAlcoholTrackerFragment(bundle)
        }
    }

    override fun onClickDelete(track: Track, position: Int) {
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(KEY_CALENDAR)
            ?.observe(
                viewLifecycleOwner
            ) { result ->
                if (result == KEY_CALENDAR_OK) {
                    lifecycleScope.launch {
                        viewModel.onDeleteDrink(track)
                    }
                    alcoholTrackListAdapter?.notifyItemRemoved(position)
                    initIconOnDate()
                }
            }
        findNavController().navigate(R.id.deleteDialogFragment)
    }

    private fun initAlcoholTrackByDay(eventDay: Long) = with(binding) {
        viewModel.initTracksByDay(eventDay)

        alcoholTrackListAdapter = DrinksListAdapter(
            this@CalendarFragment
        )
        drinksList.adapter = alcoholTrackListAdapter

        viewModel.tracksByDay.observe(viewLifecycleOwner, { tracks ->
            alcoholTrackListAdapter?.submitList(tracks)
            if (tracks.isNotEmpty()) {
                emptyDrinkList.toGone()
                addToStart.toGone()
            } else {
                emptyDrinkList.toVisible()
            }
            progressBar.toGone()
        })
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}