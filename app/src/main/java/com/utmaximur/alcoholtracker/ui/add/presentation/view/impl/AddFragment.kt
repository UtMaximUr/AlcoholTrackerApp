package com.utmaximur.alcoholtracker.ui.add.presentation.view.impl

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Bundle
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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.tabs.TabLayout
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.dagger.component.AlcoholTrackComponent
import com.utmaximur.alcoholtracker.dagger.factory.AddViewModelFactory
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.ui.add.presentation.view.AddView
import com.utmaximur.alcoholtracker.ui.add.presentation.view.impl.adapter.DrinkViewPagerAdapter
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
        injectDagger()
        initViewModel()
        setDrinksList()
        findViewById(view)
        return view
    }

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    private fun initViewModel() {
        val dependencyFactory: AlcoholTrackComponent =
            (requireActivity().application as App).alcoholTrackComponent
        val drinkRepository = dependencyFactory.provideDrinkRepository()
        val trackRepository = dependencyFactory.provideTrackRepository()
        val viewModel: AddViewModel by viewModels {
            AddViewModelFactory(drinkRepository, trackRepository)
        }
        this.viewModel = viewModel
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
            if (viewModel.checkIsEmptyField(getPrice(), getDate())) {
                viewModel.onSaveButtonClick(
                    AlcoholTrack(
                        getIdDrink(),
                        getDrink(),
                        getVolume(),
                        getQuantity(),
                        getDegree(),
                        getPrice(),
                        getDate(),
                        getIcon()
                    )
                )
                addFragmentListener?.closeFragment()
                addFragmentListener!!.onShowNavigationBar()
            }else{
                showWarningEmptyField()
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
            if (viewModel.checkIsEmptyFieldPrice(getPrice())) {
                totalMoneyText.text = viewModel.getTotalMoney(getQuantity(), getPrice())
            }
        }

        //Градус
        degreeNumberPicker.maxValue = viewModel.getFloatDegree().size
        degreeNumberPicker.minValue = 1
        degreeNumberPicker.displayedValues = viewModel.getFloatDegree()
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
            addDateButton.text = viewModel.setDateOnButton(
                requireContext(),
                Date()
            )
            viewModel.date = Date().time
            todayButton.visibility = GONE
        }

        priceEditText.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                if (viewModel.checkIsEmptyFieldPrice(getPrice())) {
                    totalMoneyText.text = viewModel.getTotalMoney(getQuantity(), getPrice())
                    viewModel.price = priceEditText.text.toString().toFloat()
                    priceEditText.clearFocus()
                }
            }
            false
        }

        priceEditText.setOnFocusChangeListener { _, b ->
            if (b) {
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
        addDateButton.text = viewModel.getFormatString(requireContext(), alcoholTrack.date)
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
                viewModel.setDateOnButton(requireContext(), Date(dateAndTime.timeInMillis))
            viewModel.date = Date(dateAndTime.timeInMillis).time
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

    private fun setDrinksList() {
        viewModel.getAllDrink().observe(viewLifecycleOwner, Observer { list ->
            viewModel.drinks = list
            initUi()
        })
    }

    private fun getDrinksList(): List<Drink> {
        return viewModel.drinks
    }

    override fun showWarningEmptyField() {
        if (viewModel.price == 0.0f) {
            priceEditText.hint = getText(R.string.enter_price)
        } else if (viewModel.date == 0L) {
            val buttonAnimation =
                AnimationUtils.loadAnimation(context, R.anim.button_animation)
            addDateButton.startAnimation(buttonAnimation)
        }
    }
}