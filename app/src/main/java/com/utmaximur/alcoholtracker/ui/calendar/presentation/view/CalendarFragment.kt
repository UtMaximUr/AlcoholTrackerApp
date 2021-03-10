package com.utmaximur.alcoholtracker.ui.calendar.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.EventDay
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.dagger.component.AlcoholTrackComponent
import com.utmaximur.alcoholtracker.dagger.factory.CalendarViewModelFactory
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.ui.calendar.presentation.view.adapter.DrinksListAdapter
import com.utmaximur.alcoholtracker.ui.dialog.adddrink.AddDrinkDialogFragment
import com.utmaximur.alcoholtracker.util.alphaView
import java.util.*
import javax.inject.Inject


class CalendarFragment : Fragment(),
    DrinksListAdapter.OnDrinkAdapterListener,
    AddDrinkDialogFragment.AddDrinkDialogListener {

    @Inject
    lateinit var calendarViewModelFactory: CalendarViewModelFactory

    private var calendarFragmentListener: CalendarFragmentListener? = null

    interface CalendarFragmentListener {
        fun showEditAlcoholTrackerFragment(bundle: Bundle)
        fun showAddAlcoholTrackerFragment(bundle: Bundle?)
    }

    private lateinit var viewModel: CalendarViewModel
    private lateinit var calendarView: com.applandeo.materialcalendarview.CalendarView

    private lateinit var recyclerView: RecyclerView
    private var drinksListAdapter: RecyclerView.Adapter<*>? = null
    private lateinit var addToStartText: TextView
    private lateinit var emptyDrinkListText: TextView
    private lateinit var addButton: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_calendar, container, false)

        injectDagger()
        initViewModel()
        initUI(view)

        return view
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

    private fun findViewById(view: View) {
        calendarView = view.findViewById(R.id.calendar_view)
        addButton = view.findViewById(R.id.add_button)
        addToStartText = view.findViewById(R.id.add_to_start)
        emptyDrinkListText = view.findViewById(R.id.empty_drink_list)
        recyclerView = view.findViewById(R.id.drinks_list)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initUI(view: View) {
        findViewById(view)

        addButton.setOnClickListener {
            if (viewModel.getSelectDate() != 0L) {
                val dialogFragment = AddDrinkDialogFragment()
                dialogFragment.setListener(this)
                val manager = requireActivity().supportFragmentManager
                dialogFragment.show(manager, "myDialog")
            } else {
                calendarFragmentListener?.showAddAlcoholTrackerFragment(null)
            }
        }

        calendarView.setOnDayClickListener { eventDay ->
            getAlcoholTrackByDay(eventDay.calendar.timeInMillis)
            viewModel.setSelectDate(eventDay.calendar.timeInMillis)
        }
        initCalendar()
    }

    private fun initCalendar() {
        // добавление иконок алкоголя в календарь
        setIconOnDate()
//        viewModel.getTracks().observe(viewLifecycleOwner, Observer { list ->
//            if (list.isNotEmpty()) {
//                addToStartText.visibility = View.GONE
//            }
//        })
        drinksListAdapter = DrinksListAdapter(getAlcoholTrackByDay(Date().time), this)
        recyclerView.adapter = drinksListAdapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        calendarFragmentListener = context as CalendarFragmentListener
    }

    override fun onResume() {
        super.onResume()
        if(viewModel.getSelectDate() != 0L) {
            viewModel.setSelectDate(0L)
        }
    }

    override fun onEdit(date: Long) {
        val bundle = Bundle()
        viewModel.getTrack(date).observe(viewLifecycleOwner, Observer { track ->
            bundle.putParcelable("drink", track)
            calendarFragmentListener?.showEditAlcoholTrackerFragment(bundle)
        })
    }

    override fun onDelete(alcoholTrack: AlcoholTrack) {
        viewModel.deleteDrink(alcoholTrack)
        getAlcoholTrackByDay(Date().time)
        setIconOnDate()
        if (getAlcoholTrackByDay(Date().time).isEmpty()) {
            emptyDrinkListText.visibility = View.VISIBLE
        }
    }

    private fun getAlcoholTrackByDay(eventDay: Long): MutableList<AlcoholTrack> {
        val alcoholTrack: ArrayList<AlcoholTrack> = ArrayList()
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = eventDay
        val startTimeDay: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val endTimeDay: Int = startTimeDay + 1
        val month: Int = calendar.get(Calendar.MONTH)
        viewModel.getTracks().observe(viewLifecycleOwner, Observer { list ->
            list.forEach {
                calendar.timeInMillis = it.date
                if (calendar.get(Calendar.DAY_OF_MONTH) in startTimeDay until endTimeDay && calendar.get(
                        Calendar.MONTH
                    ) == month
                ) {
                    alcoholTrack.add(it)
                }
            }
            drinksListAdapter = DrinksListAdapter(
                alcoholTrack,
                this@CalendarFragment
            )
            recyclerView.adapter = drinksListAdapter
            recyclerView.alphaView(requireContext())
            if (alcoholTrack.isNotEmpty()) {
                emptyDrinkListText.visibility = View.INVISIBLE
            } else {
                if (list.isEmpty()) {
                    addToStartText.visibility = View.VISIBLE
                    addToStartText.alphaView(requireContext())
                } else {
                    emptyDrinkListText.visibility = View.VISIBLE
                    emptyDrinkListText.alphaView(requireContext())
                }
            }
        })
        return alcoholTrack
    }

    private fun setIconOnDate() {
        val events: MutableList<EventDay> = ArrayList()
        viewModel.getTracks().observe(viewLifecycleOwner, Observer { list ->
            events.clear()
            list.forEach {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = it.date
                events.add(EventDay(calendar, it.icon))
            }
            calendarView.setEvents(events)
        })
    }

    override fun addDrinkDialogPositiveClick() {
        val bundle = Bundle()
        bundle.putLong("selectDate", viewModel.getSelectDate())
        calendarFragmentListener?.showAddAlcoholTrackerFragment(bundle)
    }

    override fun addDrinkDialogNegativeClick() {
        calendarFragmentListener?.showAddAlcoholTrackerFragment(null)
    }
}