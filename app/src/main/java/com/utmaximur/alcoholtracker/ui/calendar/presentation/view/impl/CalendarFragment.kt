package com.utmaximur.alcoholtracker.ui.calendar.presentation.view.impl

import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.EventDay
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.ui.calendar.presentation.presenter.CalendarPresenter
import com.utmaximur.alcoholtracker.ui.calendar.presentation.presenter.factory.CalendarPresenterFactory
import com.utmaximur.alcoholtracker.ui.calendar.presentation.view.CalendarView
import com.utmaximur.alcoholtracker.ui.calendar.presentation.view.impl.adapter.DrinksListAdapter
import com.utmaximur.alcoholtracker.ui.main.presentation.view.impl.MainActivity
import java.util.*


class CalendarFragment : Fragment(),
    CalendarView,
    DrinksListAdapter.OnDrinkAdapterListener {

    private lateinit var viewModel: CalendarViewModel
    private lateinit var presenter: CalendarPresenter
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

        viewModel =
            ViewModelProvider.AndroidViewModelFactory(activity?.applicationContext as Application)
                .create(CalendarViewModel::class.java)
        presenter =
            CalendarPresenterFactory.createPresenter(activity?.applicationContext as Application)
        presenter.onAttachView(this)
        presenter.viewIsReady()

        initUI(view)

        return view
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
            (requireActivity() as MainActivity).showAddAlcoholTrackerFragment()
        }

        calendarView.setOnDayClickListener { eventDay ->
            drinksListAdapter = DrinksListAdapter(
                presenter.getAlcoholTrackByDay(eventDay.calendar.timeInMillis),
                this@CalendarFragment
            )
            recyclerView.adapter = drinksListAdapter
            if (presenter.getAlcoholTrackByDay(eventDay.calendar.timeInMillis).isEmpty()) {
                emptyDrinkListText.visibility = View.VISIBLE
            } else {
                emptyDrinkListText.visibility = View.INVISIBLE
            }
        }

        Handler(Looper.getMainLooper()).post {
            // добавление иконок алкоголя в календарь
            presenter.setIconOnDate()
            // добавдение напитков в список
            presenter.initRealmWithData(requireContext())
            drinksListAdapter = DrinksListAdapter(presenter.getAlcoholTrackByDay(Date().time), this)
            recyclerView.adapter = drinksListAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        if (presenter.getDrinkByMonth(Date().time).isNotEmpty()) {
            addToStartText.visibility = View.GONE
            if (presenter.getAlcoholTrackByDay(Date().time).isEmpty()) {
                emptyDrinkListText.visibility = View.VISIBLE
            } else {
                emptyDrinkListText.visibility = View.INVISIBLE
            }
        }
    }

    override fun onEdit(date: Long) {
        (requireActivity() as MainActivity).showEditAlcoholTrackerFragment(presenter.getDrink(date))
    }

    override fun onDelete(id: String) {
        presenter.deleteDrink(id)
        presenter.setIconOnDate()
        if (presenter.getAlcoholTrackByDay(Date().time).isEmpty()) {
            emptyDrinkListText.visibility = View.VISIBLE
        }
    }

    override fun setIconOnDate(events: MutableList<EventDay>){
        calendarView.setEvents(events)
    }
}