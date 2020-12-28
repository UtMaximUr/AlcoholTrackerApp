package com.utmaximur.alcoholtracker.ui.calendar.presentation.view.impl

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Bundle
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
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.ui.calendar.presentation.presenter.CalendarPresenter
import com.utmaximur.alcoholtracker.ui.calendar.presentation.presenter.factory.CalendarPresenterFactory
import com.utmaximur.alcoholtracker.ui.calendar.presentation.view.CalendarView
import com.utmaximur.alcoholtracker.ui.calendar.presentation.view.impl.adapter.DrinksListAdapter
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


class CalendarFragment : Fragment(),
    CalendarView,
    DrinksListAdapter.OnDrinkAdapterListener {

    private var calendarFragmentListener: CalendarFragmentListener? = null

    interface CalendarFragmentListener {
        fun showEditAlcoholTrackerFragment(bundle: Bundle)
        fun showAddAlcoholTrackerFragment()
    }

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
            calendarFragmentListener?.showAddAlcoholTrackerFragment()
        }

        calendarView.setOnDayClickListener { eventDay ->
            Flowable.fromCallable {
                presenter.getAlcoholTrackByDay(eventDay.calendar.timeInMillis)
            }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { list: MutableList<AlcoholTrack> ->
                        drinksListAdapter = DrinksListAdapter(
                            list,
                            this@CalendarFragment
                        )
                        recyclerView.adapter = drinksListAdapter

                        if (list.isNotEmpty()) {
                            emptyDrinkListText.visibility = View.INVISIBLE
                        } else {
                            if (addToStartText.visibility != View.VISIBLE) {
                                emptyDrinkListText.visibility = View.VISIBLE
                            }
                        }
                    }
                ) { obj: Throwable ->
                    obj.printStackTrace()
                    Log.e("fix", "error rx")
                }
        }
        initCalendar()
    }

    @SuppressLint("CheckResult")
    private fun initCalendar() {
        // добавление иконок алкоголя в календарь
        Flowable.fromCallable {
            presenter.setIconOnDate()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        Flowable.fromCallable {
            presenter.getTracks()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list: MutableList<AlcoholTrack> ->
                    if (list.isNotEmpty()) {
                        addToStartText.visibility = View.GONE
                    }
                }
            ) { obj: Throwable -> obj.printStackTrace() }

        Flowable.fromCallable {
            presenter.getAlcoholTrackByDay(Date().time)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list: MutableList<AlcoholTrack> ->
                    drinksListAdapter = DrinksListAdapter(list, this)
                    recyclerView.adapter = drinksListAdapter
                    if (list.isEmpty()) {
                        if (addToStartText.visibility != View.VISIBLE) {
                            emptyDrinkListText.visibility = View.VISIBLE
                        }
                    } else {
                        emptyDrinkListText.visibility = View.INVISIBLE
                    }
                }
            ) { obj: Throwable -> obj.printStackTrace() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        calendarFragmentListener = context as CalendarFragmentListener
    }

    @SuppressLint("CheckResult")
    override fun onEdit(date: Long) {
        Flowable.fromCallable {
            presenter.getDrink(date)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { drink: AlcoholTrack? ->
                    val bundle = Bundle()
                    bundle.putParcelable("drink", drink)
                    calendarFragmentListener?.showEditAlcoholTrackerFragment(bundle)
                }
            ) { obj: Throwable -> obj.printStackTrace() }
    }

    override fun onDelete(alcoholTrack: AlcoholTrack) {
        presenter.deleteDrink(alcoholTrack)
        presenter.setIconOnDate()
        if (presenter.getAlcoholTrackByDay(Date().time).isEmpty()) {
            emptyDrinkListText.visibility = View.VISIBLE
        }
    }

    override fun setIconOnDate(events: MutableList<EventDay>) {
        calendarView.setEvents(events)
    }
}