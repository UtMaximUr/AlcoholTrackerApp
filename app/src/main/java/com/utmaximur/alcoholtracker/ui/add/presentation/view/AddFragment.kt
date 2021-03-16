package com.utmaximur.alcoholtracker.ui.add.presentation.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
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
import androidx.constraintlayout.widget.ConstraintLayout
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
import com.utmaximur.alcoholtracker.ui.add.presentation.view.adapter.DrinkViewPagerAdapter
import com.utmaximur.alcoholtracker.ui.add.presentation.view.adapter.DrinkViewPagerAdapter.AddDrinkListener
import com.utmaximur.alcoholtracker.ui.calculator.view.CalculatorFragment
import com.utmaximur.alcoholtracker.ui.calculator.view.CalculatorFragment.CalculatorListener
import com.utmaximur.alcoholtracker.util.alphaView
import com.utmaximur.alcoholtracker.util.dpToPx
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.*


class AddFragment : Fragment(), CalculatorListener, AddDrinkListener {

    private var addFragmentListener: AddFragmentListener? = null

    interface AddFragmentListener {
        fun onHideNavigationBar()
        fun onShowNavigationBar()
        fun onShowAddNewDrinkFragment()
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

    private lateinit var containerFragment: ConstraintLayout

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
        initUi(view)
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

        containerFragment = view.findViewById(R.id.container_calculator)
    }

    private fun initUi(view: View) {
        findViewById(view)

        toolbar.setNavigationOnClickListener {
            addFragmentListener?.closeFragment()
        }

        toolbar.setOnMenuItemClickListener {
            if (viewModel.checkIsEmptyField(getPrice(), getDate())) {
                viewModel.onSaveButtonClick(
                    AlcoholTrack(
                        getIdDrink(),
                        getDrink(),
                        getVolume()!!,
                        getQuantity(),
                        getDegree(),
                        getPrice(),
                        getDate(),
                        getIcon()
                    )
                )
                addFragmentListener?.closeFragment()
                addFragmentListener!!.onShowNavigationBar()
            } else {
                showWarningEmptyField()
            }
            true
        }

        // выбор напитков
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
                if (getDrinksList().size != position) {
                    setDrinkDegreeArray(position)
                    setDrinkVolumeArray(position)
                }
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


        viewModel.getAllDrink().observe(viewLifecycleOwner, Observer { list ->
            //Градус
            // устанавливаем по умолчанию первый напиток
            degreeNumberPicker.displayedValues = null
            degreeNumberPicker.maxValue = list.first().degree.size - 1
            degreeNumberPicker.displayedValues = list.first().degree.toTypedArray()
            degreeNumberPicker.value = 1
            changeValueByOne(degreeNumberPicker)

            //Объем
            // устанавливаем по умолчанию первый напиток
            volumeNumberPicker.displayedValues = null
            volumeNumberPicker.maxValue = list.first().volume.size - 1
            volumeNumberPicker.displayedValues = list.first().volume.toTypedArray()
            volumeNumberPicker.value = 1
            changeValueByOne(volumeNumberPicker)
            setVolume(list.first().volume.toList())
        })

        addDateButton.setOnClickListener {
            if (viewModel.date != 0L) {
                dateAndTime.timeInMillis = viewModel.date
            }
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


        priceEditText.setOnClickListener {
            if (containerFragment.height == 0) {
                val calculatorFragment = CalculatorFragment()
                calculatorFragment.setListener(this@AddFragment)
                activity?.supportFragmentManager?.beginTransaction()
                    ?.add(R.id.container_calculator, calculatorFragment)
                    ?.setCustomAnimations(R.anim.fragment_open_enter, R.anim.fragment_close_exit)
                    ?.commit()
                animateViewHeight(containerFragment, 245.dpToPx())
            }
        }

        priceEditText.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                if (viewModel.checkIsEmptyFieldPrice(getPrice())) {
                    if (priceEditText.text.isNotEmpty()) {
                        totalMoneyText.text = viewModel.getTotalMoney(getQuantity(), getPrice())
                        viewModel.price = priceEditText.text.toString().toFloat()
                    } else {
                        totalMoneyText.text = getText(R.string.add_empty)
                    }
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
    }

    @SuppressLint("DiscouragedPrivateApi")
    private fun changeValueByOne(numberPicker: NumberPicker) {
        try {
            val method: Method = numberPicker.javaClass
                .getDeclaredMethod("changeValueByOne", Boolean::class.javaPrimitiveType)
            method.isAccessible = true
            method.invoke(numberPicker, true)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    }

    private fun animateViewHeight(view: ConstraintLayout, targetHeight: Int) {
        val animator: ValueAnimator = ObjectAnimator.ofInt(view.height, targetHeight)
        animator.addUpdateListener { animation ->
            val params = view.layoutParams
            params.height = animation.animatedValue as Int
            view.layoutParams = params
        }
        animator.start()
    }

    private fun setDrinkDegreeArray(position: Int) {
        val drink: Drink = getDrinksList()[position]
        degreeNumberPicker.displayedValues = null
        degreeNumberPicker.value = 1
        degreeNumberPicker.maxValue = drink.degree.size - 1
        degreeNumberPicker.displayedValues = drink.degree.toTypedArray()
        setDegreeList(drink.degree)
    }

    private fun setDrinkVolumeArray(position: Int) {
        val drink: Drink = getDrinksList()[position]
        volumeNumberPicker.displayedValues = null
        volumeNumberPicker.value = 1
        volumeNumberPicker.maxValue = drink.volume.size - 1
        volumeNumberPicker.displayedValues = drink.volume.toTypedArray()
        setVolume(drink.volume)
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
                    it.volume.indexOf(alcoholTrack.volume)
                setDrinkDegreeArray(position)
                degreeNumberPicker.value = it.degree.indexOf(alcoholTrack.degree) + 1
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

    private fun getIdDrink(): String {
        return viewModel.id
    }

    private fun getDrink(): String {
        return getDrinksList()[drinksPager.currentItem].drink
    }

    private fun getQuantity(): Int {
        return quantityNumberPicker.value
    }

    private fun getDegree(): String {
        return degreeNumberPicker.displayedValues[degreeNumberPicker.value - 1].toString()
    }

    private fun getPrice(): Float {
        return if (priceEditText.text.toString().isEmpty()) {
            viewModel.price
        } else {
            priceEditText.text.toString().toFloat()
        }
    }

    private fun getDate(): Long {
        return viewModel.date
    }

    private fun getVolume(): String? {
        return getVolumeList()[volumeNumberPicker.value]
    }

    private fun setVolume(volume: List<String?>) {
        viewModel.volums = volume
    }

    private fun setDegreeList(degree: List<String?>) {
        viewModel.degrees = degree
    }

    private fun getVolumeList(): List<String?> {
        return viewModel.volums
    }

    private fun getIcon(): String {
        return getDrinksList()[drinksPager.currentItem].icon
    }

    private fun setDrinksList() {
        viewModel.getAllDrink().observe(viewLifecycleOwner, Observer { list ->
            viewModel.drinks = list
            val adapter = DrinkViewPagerAdapter(getDrinksList(), requireContext())
            adapter.setListener(this)
            drinksPager.adapter = adapter
            drinksPager.alphaView(requireContext())
            if (arguments != null) {
                if (arguments?.containsKey("selectDate")!! && arguments?.getLong("selectDate") != 0L) {
                    addDateButton.text = viewModel.setDateOnButton(
                        requireContext(),
                        Date(requireArguments().getLong("selectDate"))
                    )
                    viewModel.date = requireArguments().getLong("selectDate")
                    todayButton.visibility = GONE
                } else {
                    setEditArguments()
                }
            }
        })
    }

    private fun getDrinksList(): List<Drink> {
        return viewModel.drinks
    }

    private fun showWarningEmptyField() {
        if (viewModel.price == 0.0f) {
            priceEditText.hint = getText(R.string.enter_price)
        } else if (viewModel.date == 0L) {
            val buttonAnimation =
                AnimationUtils.loadAnimation(context, R.anim.button_animation)
            addDateButton.startAnimation(buttonAnimation)
        }
    }

    override fun getValueCalculating(value: String) {
        priceEditText.setText(value)
    }

    override fun closeCalculator() {
        animateViewHeight(containerFragment, 0)
    }

    override fun addNewDrink() {
        addFragmentListener?.onShowAddNewDrinkFragment()
    }

    override fun deleteDrink(drink: Drink) {
        viewModel.deleteDrink(drink)
    }

    override fun editDrink(drink: Drink) {

    }
}