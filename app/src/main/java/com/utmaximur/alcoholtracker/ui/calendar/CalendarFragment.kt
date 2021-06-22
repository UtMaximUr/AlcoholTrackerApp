package com.utmaximur.alcoholtracker.ui.calendar

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.EventDay
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.databinding.FragmentCalendarBinding
import com.utmaximur.alcoholtracker.di.component.AlcoholTrackComponent
import com.utmaximur.alcoholtracker.di.factory.CalendarViewModelFactory
import com.utmaximur.alcoholtracker.ui.calendar.adapter.DrinksListAdapter
import com.utmaximur.alcoholtracker.util.*
import java.util.*
import javax.inject.Inject


class CalendarFragment : Fragment(),
    DrinksListAdapter.OnDrinkAdapterListener {

    @Inject
    lateinit var calendarViewModelFactory: CalendarViewModelFactory

    private var calendarFragmentListener: CalendarFragmentListener? = null

    interface CalendarFragmentListener {
        fun showEditAlcoholTrackerFragment(bundle: Bundle)
        fun showAddAlcoholTrackerFragment(bundle: Bundle?)
    }

    private lateinit var viewModel: CalendarViewModel
    private lateinit var binding: FragmentCalendarBinding
    private var alcoholTrackListAdapter: RecyclerView.Adapter<*>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(layoutInflater)
        injectDagger()
        initViewModel()
        initUI()
        return binding.root
    }

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    private fun initViewModel() {
        val dependencyFactory: AlcoholTrackComponent =
            (requireActivity().application as App).alcoholTrackComponent
        val trackRepository = dependencyFactory.provideTrackRepository()

        val viewModel: CalendarViewModel by viewModels {
            CalendarViewModelFactory(trackRepository)
        }
        this.viewModel = viewModel
    }

    private fun initUI() {
        binding.addButton.setOnClickListener {
            if (viewModel.getSelectDate() != 0L) {
                val navController = findNavController()
                navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
                    KEY_CALENDAR_DATA)?.observe(
                    viewLifecycleOwner) { result ->
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

        binding.calendarView.setOnDayClickListener { eventDay ->
            getAlcoholTrackByDay(eventDay.calendar.timeInMillis)
            viewModel.setSelectDate(eventDay.calendar.timeInMillis)
        }
        initCalendar()
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
        viewModel.getTrack(date).observe(viewLifecycleOwner, { track ->
            bundle.putParcelable(DRINK, track)
            calendarFragmentListener?.showEditAlcoholTrackerFragment(bundle)
        })
    }

    override fun onDelete(alcoholTrack: AlcoholTrack) {
        val navController = findNavController()
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>(KEY_CALENDAR)
            ?.observe(
                viewLifecycleOwner
            ) { result ->
                if (result == KEY_CALENDAR_OK) {
                    viewModel.deleteDrink(alcoholTrack)
                    alcoholTrackListAdapter?.notifyDataSetChanged()
                    setIconOnDate()
                    getAlcoholTrackByDay(alcoholTrack.date)
                }
            }
        navController.navigate(R.id.deleteDialogFragment)
    }

    private fun getAlcoholTrackByDay(eventDay: Long): MutableList<AlcoholTrack> {
        val alcoholTrack: ArrayList<AlcoholTrack> = ArrayList()
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = eventDay
        val startTimeDay: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val endTimeDay: Int = startTimeDay + 1
        val month: Int = calendar.get(Calendar.MONTH)
        viewModel.getTracks().observe(viewLifecycleOwner, { list ->
            alcoholTrack.clear()
            list.forEach {
                calendar.timeInMillis = it.date
                if (calendar.get(Calendar.DAY_OF_MONTH) in startTimeDay until endTimeDay && calendar.get(
                        Calendar.MONTH
                    ) == month
                ) {
                    alcoholTrack.add(it)
                }
            }
            alcoholTrackListAdapter = DrinksListAdapter(
                alcoholTrack,
                this@CalendarFragment
            )
            binding.drinksList.adapter = alcoholTrackListAdapter
            binding.drinksList.alphaView()
            if (alcoholTrack.isNotEmpty()) {
                binding.emptyDrinkList.toGone()
            } else {
                if (list.isEmpty()) {
                    binding.addToStart.toVisible()
                    binding.addToStart.alphaView()
                } else {
                    binding.emptyDrinkList.toVisible()
                    binding.emptyDrinkList.alphaView()
                }
            }
        })
        return alcoholTrack
    }

    private fun setIconOnDate() {
        val events: MutableList<EventDay> = ArrayList()
        viewModel.getTracks().observe(viewLifecycleOwner, { list ->
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
}