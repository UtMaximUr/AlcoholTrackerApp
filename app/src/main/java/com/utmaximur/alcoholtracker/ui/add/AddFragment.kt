package com.utmaximur.alcoholtracker.ui.add

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.tabs.TabLayout
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.dagger.component.AlcoholTrackComponent
import com.utmaximur.alcoholtracker.dagger.factory.AddViewModelFactory
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.model.Icon
import com.utmaximur.alcoholtracker.ui.add.adapter.DrinkViewPagerAdapter
import com.utmaximur.alcoholtracker.ui.add.adapter.DrinkViewPagerAdapter.AddDrinkListener
import com.utmaximur.alcoholtracker.ui.calculator.CalculatorFragment
import com.utmaximur.alcoholtracker.ui.calculator.CalculatorFragment.CalculatorListener
import com.utmaximur.alcoholtracker.ui.dialog.delete.DeleteDialogFragment
import com.utmaximur.alcoholtracker.util.*
import java.util.*


class AddFragment : Fragment(), CalculatorListener, AddDrinkListener {

    private var addFragmentListener: AddFragmentListener? = null

    interface AddFragmentListener {
        fun onHideNavigationBar()
        fun onShowNavigationBar()
        fun onShowAddNewDrinkFragment()
        fun onShowEditNewDrinkFragment(bundle: Bundle)
        fun closeFragment()
    }

    private lateinit var toolbar: Toolbar

    private lateinit var viewModel: AddViewModel

    private lateinit var totalMoneyText: TextView
    private lateinit var eventEditText: EditText
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
    ): View {
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
        eventEditText = view.findViewById(R.id.event_edit_text)
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
            hideKeyboard()
            addFragmentListener?.closeFragment()
        }

        toolbar.setOnMenuItemClickListener {
            if (drinksPager.currentItem != getDrinksList().size) {
                viewModel.onSaveButtonClick(
                    AlcoholTrack(
                        getIdDrink(),
                        getDrink(),
                        getVolume()!!,
                        getQuantity(),
                        getDegree(),
                        getEvent(),
                        getPrice(),
                        getDate(),
                        getIcon()
                    )
                )
            }
            addFragmentListener?.closeFragment()
            addFragmentListener!!.onShowNavigationBar()
            true
        }

        // select drink
        dotsIndicator.setupWithViewPager(drinksPager, true)
        drinksPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                hideKeyboard()
            }

            override fun onPageSelected(position: Int) {
                if (getDrinksList().size != position) {
                    setDrinkDegreeArray(position)
                    setDrinkVolumeArray(position)
                }
            }
        })

        // Quantity
        quantityNumberPicker.maxValue = 10
        quantityNumberPicker.minValue = 1
        quantityNumberPicker.setOnScrollListener { _, _ ->
            if (viewModel.checkIsEmptyFieldPrice(getPrice())) {
                totalMoneyText.text = viewModel.getTotalMoney(getQuantity(), getPrice())
            }
        }


        viewModel.getAllDrink().observe(viewLifecycleOwner, { list ->
            // Degree
            // set default first drink
            initNumberPicker(degreeNumberPicker)
            degreeNumberPicker.displayedValues = null
            degreeNumberPicker.maxValue = list.first().degree.size - 1
            degreeNumberPicker.displayedValues = list.first().degree.toTypedArray()
            degreeNumberPicker.value = 1

            // Volume
            // set default first drink
            initNumberPicker(volumeNumberPicker)
            volumeNumberPicker.displayedValues = null
            volumeNumberPicker.maxValue = list.first().volume.size - 1
            volumeNumberPicker.displayedValues =
                list.first().volume.setVolumeUnit(requireContext()).toTypedArray()
            volumeNumberPicker.value = 1
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
            addDateButton.text = Date().time.formatDate(requireContext())
            viewModel.date = Date().time
            todayButton.toGone()
        }

        eventEditText.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                return@OnEditorActionListener true
            }
            true
        })

        priceEditText.setOnClickListener {
            if (containerFragment.height == 0) {
                val calculatorFragment = CalculatorFragment()
                val bundle = Bundle()
                bundle.putString(PRICE_DRINK, priceEditText.text.toString())
                calculatorFragment.arguments = bundle
                calculatorFragment.setListener(this@AddFragment)
                activity?.supportFragmentManager?.beginTransaction()
                    ?.add(R.id.container_calculator, calculatorFragment)
                    ?.setCustomAnimations(R.anim.fragment_open_enter, R.anim.fragment_close_exit)
                    ?.commit()
                animateViewHeight(containerFragment, ANIMATE_HEIGHT.dpToPx())
            }
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

    private fun hideKeyboard(){
        eventEditText.hideKeyboard()
    }

    private fun setDrinkDegreeArray(position: Int) {
        val drink: Drink = getDrinksList()[position]
        degreeNumberPicker.displayedValues = null
        degreeNumberPicker.minValue = 0
        degreeNumberPicker.value = 1
        degreeNumberPicker.maxValue = drink.degree.indexOf(drink.degree.last())
        degreeNumberPicker.displayedValues = drink.degree.toTypedArray()
        setDegreeList(drink.degree)
    }

    private fun setDrinkVolumeArray(position: Int) {
        val drink: Drink = getDrinksList()[position]
        volumeNumberPicker.displayedValues = null
        volumeNumberPicker.minValue = 0
        volumeNumberPicker.value = 1
        volumeNumberPicker.maxValue = drink.volume.indexOf(drink.volume.last())
        volumeNumberPicker.displayedValues =
            drink.volume.setVolumeUnit(requireContext()).toTypedArray()
        setVolume(drink.volume)
    }

    private fun setEditArguments() {

        val alcoholTrack: AlcoholTrack? = requireArguments().getParcelable(DRINK)
        toolbar.title = getString(R.string.edit_drink_title)
        viewModel.id = alcoholTrack?.id.toString()
        viewModel.date = alcoholTrack?.date!!

        viewModel.getAllDrink().observe(viewLifecycleOwner, { list ->
            list.forEach { drink ->
                if (drink.drink == alcoholTrack.drink) {
                    val position = list.indexOf(drink)
                    // set position view pager
                    drinksPager.currentItem = position
                    dotsIndicator.getTabAt(position)?.select()
                    // set quantity drink
                    quantityNumberPicker.value = alcoholTrack.quantity
                    // set volume drink
                    setDrinkVolumeArray(position)
                    volumeNumberPicker.value = drink.volume.indexOf(alcoholTrack.volume)
                    initNumberPicker(volumeNumberPicker)
                    // set degree drink
                    setDrinkDegreeArray(position)
                    degreeNumberPicker.value = drink.degree.indexOf(alcoholTrack.degree)
                    initNumberPicker(degreeNumberPicker)
                }
            }
        })

        eventEditText.setText(alcoholTrack.event)
        priceEditText.setText(alcoholTrack.price.toString())
        addDateButton.text = alcoholTrack.date.formatDate(requireContext())
        todayButton.toGone()

        totalMoneyText.text =
            (alcoholTrack.price.times(alcoholTrack.quantity)).toString()
    }

    private fun initNumberPicker(numberPicker: NumberPicker) {
        numberPicker.children.iterator().forEach {
            if (it is EditText) it.width = LinearLayout.LayoutParams.WRAP_CONTENT
        }
    }

    private var onDateSetListener =
        OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            dateAndTime[Calendar.YEAR] = year
            dateAndTime[Calendar.MONTH] = monthOfYear
            dateAndTime[Calendar.DAY_OF_MONTH] = dayOfMonth
            viewModel.date = dateAndTime.timeInMillis
            addDateButton.text = dateAndTime.timeInMillis.formatDate(requireContext())
            viewModel.date = Date(dateAndTime.timeInMillis).time
            todayButton.toGone()
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
        return degreeNumberPicker.displayedValues[degreeNumberPicker.value].toString()
    }

    private fun getEvent(): String {
        return eventEditText.text.toString()
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
        viewModel.volumes = volume
    }

    private fun setDegreeList(degree: List<String?>) {
        viewModel.degrees = degree
    }

    private fun getVolumeList(): List<String?> {
        return viewModel.volumes
    }

    private fun getIcon(): String {
        return getDrinksList()[drinksPager.currentItem].icon
    }

    private fun setDrinksList() {
        viewModel.getAllDrink().observe(viewLifecycleOwner, { list ->
            viewModel.drinks = list
            val adapter = DrinkViewPagerAdapter(list, requireContext())
            adapter.setListener(this)
            drinksPager.adapter = adapter
            drinksPager.alphaView()
            if (arguments != null) {
                if (arguments?.containsKey(SELECT_DAY)!! && arguments?.getLong(SELECT_DAY) != 0L) {
                    val selectDate = requireArguments().getLong(SELECT_DAY)
                    addDateButton.text = selectDate.formatDate(requireContext())
                    viewModel.date = selectDate
                    todayButton.toGone()
                } else {
                    setEditArguments()
                }
            }
        })
    }

    private fun getDrinksList(): List<Drink> {
        return viewModel.drinks
    }

    override fun getValueCalculating(value: String) {
        priceEditText.setText(value)
        if (value != "") {
            totalMoneyText.text = (quantityNumberPicker.value * value.toInt()).toString()
        } else {
            totalMoneyText.text = "0"
        }
    }

    override fun closeCalculator() {
        animateViewHeight(containerFragment, 0)
    }

    override fun addNewDrink() {
        addFragmentListener?.onShowAddNewDrinkFragment()
    }

    override fun deleteDrink(drink: Drink) {
        val deleteFragment = DeleteDialogFragment {
            viewModel.deleteDrink(drink)
        }
        deleteFragment.show(requireActivity().supportFragmentManager, deleteFragment.tag)
    }

    override fun editDrink(drink: Drink) {
        val bundle = Bundle()
        bundle.putParcelable(EDIT_DRINK, drink)
        addFragmentListener?.onShowEditNewDrinkFragment(bundle)
    }
}