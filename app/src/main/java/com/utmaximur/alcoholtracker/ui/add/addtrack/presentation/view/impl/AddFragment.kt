package com.utmaximur.alcoholtracker.ui.add.addtrack.presentation.view.impl

import android.annotation.SuppressLint
import android.app.Application
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.tabs.TabLayout
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.ui.add.addtrack.presentation.presenter.AddPresenter
import com.utmaximur.alcoholtracker.ui.add.addtrack.presentation.presenter.factory.AddPresenterFactory
import com.utmaximur.alcoholtracker.ui.add.addtrack.presentation.view.AddView
import com.utmaximur.alcoholtracker.ui.add.addtrack.presentation.view.impl.adapter.DrinkViewPagerAdapter
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*


class AddFragment : Fragment(),
    AddView {

    private var addFragmentListener: AddFragmentListener? = null

    interface AddFragmentListener {
        fun onHideNavigationBar()
        fun onShowNavigationBar()
        fun closeFragment()
    }

    private lateinit var toolbar: Toolbar

    private lateinit var viewModel: AddViewModel
    private lateinit var presenter: AddPresenter

    private lateinit var totalMoneyText: TextView
    private lateinit var priceEditText: EditText

    private lateinit var degreeNumberPicker: NumberPicker
    private lateinit var quantityNumberPicker: NumberPicker
    private lateinit var volumeNumberPicker: NumberPicker

    private lateinit var datePecker: DatePickerDialog

    private lateinit var addDateButton: Button
    private lateinit var todayButton: Button

    private lateinit var drinksPager: ViewPager
    private lateinit var dotsIndicator: TabLayout

    private var dateAndTime = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_add, container, false)

        viewModel =
            ViewModelProvider.AndroidViewModelFactory(activity?.applicationContext as Application)
                .create(AddViewModel::class.java)
        presenter =
            AddPresenterFactory.createPresenter(activity?.applicationContext as Application)
        presenter.onAttachView(this)

        setDrinksList()
        findViewById(view)
        return view
    }

    private fun findViewById(view: View) {

        toolbar = view.findViewById(R.id.toolbar)

        addDateButton = view.findViewById(R.id.add_date_button)
        todayButton = view.findViewById(R.id.today_button)
        totalMoneyText = view.findViewById(R.id.total_money_text)
        priceEditText = view.findViewById(R.id.price_edit_text)

        degreeNumberPicker = view.findViewById(R.id.degree_number_picker)
        quantityNumberPicker = view.findViewById(R.id.quantity_number_picker)
        volumeNumberPicker = view.findViewById(R.id.volume_number_picker)

        drinksPager = view.findViewById(R.id.view_pager_drinks)
        dotsIndicator = view.findViewById(R.id.view_pager_indicator)
    }

    private fun initUi() {

        toolbar.setNavigationOnClickListener {
            addFragmentListener?.closeFragment()
        }

        toolbar.setOnMenuItemClickListener {
            if (presenter.checkIsEmptyField()) {
                Flowable.fromCallable {
                    presenter.onSaveButtonClick()
                }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete {
                        addFragmentListener?.closeFragment()
                        addFragmentListener!!.onShowNavigationBar()
                    }
                    .subscribe()
            }
            true
        }

        // выбор напитков
        drinksPager.adapter = DrinkViewPagerAdapter(getDrinksList(), requireContext())
        dotsIndicator.setupWithViewPager(drinksPager, true)
        drinksPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                setDrinkDegreeArray(position)
                setDrinkVolumeArray(position)
            }
        })

        //Количество
        quantityNumberPicker.maxValue = 10
        quantityNumberPicker.minValue = 1
        quantityNumberPicker.setOnScrollListener { _, _ ->
            if (presenter.checkIsEmptyFieldPrice()) {
                totalMoneyText.text = presenter.getTotalMoney()
            }
        }

        //Градус
        degreeNumberPicker.maxValue = presenter.getFloatDegree().size
        degreeNumberPicker.minValue = 1
        degreeNumberPicker.displayedValues = presenter.getFloatDegree()
        setDrinkDegreeArray(degreeNumberPicker.value) // устанавливаем по умолчанию первый напиток

        //Объем
        setDrinkVolumeArray(volumeNumberPicker.value)

        addDateButton.setOnClickListener {
            datePecker = DatePickerDialog(
                this.requireContext(), onDateSetListener,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH)
            )
            datePecker.show()
        }

        todayButton.setOnClickListener {
            addDateButton.text = presenter.setDateOnButton(
                requireContext(),
                Date()
            )
            todayButton.visibility = GONE
        }

        priceEditText.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                if (presenter.checkIsEmptyFieldPrice()) {
                    totalMoneyText.text = presenter.getTotalMoney()
                    priceEditText.clearFocus()
                }
            }
            false
        }

        priceEditText.setOnFocusChangeListener { _, b ->
            if(b){
                priceEditText.hint = ""
            }
        }

        if (arguments != null) {
            setEditArguments()
        }
    }

    private fun setDrinkDegreeArray(position: Int) {
        val drink: Drink = getDrinksList()[position]

        degreeNumberPicker.value = 0
        degreeNumberPicker.maxValue = drink.alcoholStrength.size - 1
        degreeNumberPicker.displayedValues = drink.alcoholStrength.toTypedArray()
        degreeNumberPicker.value = 1
        setDegreeList(drink.alcoholStrength)
    }

    private fun setDrinkVolumeArray(position: Int) {
        val drink: Drink = getDrinksList()[position]
        volumeNumberPicker.value = 1
        volumeNumberPicker.maxValue = resources.getStringArray(drink.alcoholVolume).size - 1
        volumeNumberPicker.displayedValues = resources.getStringArray(drink.alcoholVolume)
        setVolume(resources.getStringArray(drink.alcoholVolume).toList())
    }

    private fun setEditArguments() {

        val alcoholTrack: AlcoholTrack? = requireArguments().getParcelable("drink")
        toolbar.title = getString(R.string.edit_drink_title)
        viewModel.id = alcoholTrack?.id.toString()
        viewModel.date = alcoholTrack?.date!!

        getDrinksList().forEach {
            if (it.drink == alcoholTrack.drink) {
                val position = getDrinksList().indexOf(it)
                drinksPager.currentItem = position
                quantityNumberPicker.value = alcoholTrack.quantity
                setDrinkVolumeArray(position)
                volumeNumberPicker.value =
                    resources.getStringArray(it.alcoholVolume).indexOf(alcoholTrack.volume)
                setDrinkDegreeArray(position)
                degreeNumberPicker.value = it.alcoholStrength.indexOf(alcoholTrack.degree) + 1
                dotsIndicator.getTabAt(position)?.select()
            }
        }

        priceEditText.setText(alcoholTrack.price.toString())
        val sdf = SimpleDateFormat(getString(R.string.date_format_pattern), Locale.getDefault())
        addDateButton.text = String.format("%s", sdf.format(Date(alcoholTrack.date)))
        todayButton.visibility = GONE

        totalMoneyText.text =
            (alcoholTrack.price.times(alcoholTrack.quantity)).toString()
    }

    private var onDateSetListener =
        OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            dateAndTime[Calendar.YEAR] = year
            dateAndTime[Calendar.MONTH] = monthOfYear
            dateAndTime[Calendar.DAY_OF_MONTH] = dayOfMonth
            viewModel.date = dateAndTime.timeInMillis
            addDateButton.text =
                presenter.setDateOnButton(requireContext(), Date(dateAndTime.timeInMillis))
            todayButton.visibility = GONE
        }

    override fun onStart() {
        super.onStart()
        addFragmentListener!!.onHideNavigationBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        addFragmentListener!!.onShowNavigationBar()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        addFragmentListener = context as AddFragmentListener
    }

    override fun getIdDrink(): String {
        return viewModel.id
    }

    override fun getDrink(): String {
        return getDrinksList()[drinksPager.currentItem].drink
    }

    override fun getQuantity(): Int {
        return quantityNumberPicker.value
    }

    override fun getDegree(): String {
        return degreeNumberPicker.displayedValues[degreeNumberPicker.value].toString()
    }

    override fun getPrice(): Float {
        return if (priceEditText.text.toString().isEmpty()) {
            viewModel.price
        } else {
            priceEditText.text.toString().toFloat()
        }
    }

    override fun getDate(): Long {
        return viewModel.date
    }

    override fun getVolume(): String {
        return getVolumeList()[volumeNumberPicker.value]
    }

    override fun setVolume(volume: List<String>) {
        viewModel.volums = volume
    }

    override fun setDegreeList(degree: List<String?>) {
        viewModel.degrees = degree
    }

    override fun getDegreeList(): List<String?> {
        return viewModel.degrees
    }

    override fun getVolumeList(): List<String> {
        return viewModel.volums
    }

    override fun getIcon(): Int {
        return getDrinksList()[drinksPager.currentItem].icon
    }

    @SuppressLint("CheckResult")
    private fun setDrinksList() {
        Flowable.fromCallable {
            presenter.getAllDrink()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete {
                initUi()
            }
            .subscribe(
                { list: List<Drink> ->
                    viewModel.drinks = list
                }
            ) { obj: Throwable ->
                obj.printStackTrace()
                Log.e("fix", "error rx")
            }
    }

    private fun getDrinksList(): List<Drink> {
        return viewModel.drinks
    }

    override fun showWarningEmptyField(){
        if(viewModel.price == 0.0f){
            priceEditText.hint = getText(R.string.enter_price)
        }else if(viewModel.date == 0L){
            val buttonAnimation =
                AnimationUtils.loadAnimation(context, R.anim.button_animation)
            addDateButton.startAnimation(buttonAnimation)
        }
    }
}