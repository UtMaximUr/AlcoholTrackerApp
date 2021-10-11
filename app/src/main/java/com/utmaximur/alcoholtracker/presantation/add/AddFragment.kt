package com.utmaximur.alcoholtracker.presantation.add

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.databinding.FragmentAddBinding
import com.utmaximur.alcoholtracker.domain.entity.Drink
import com.utmaximur.alcoholtracker.domain.entity.Track
import com.utmaximur.alcoholtracker.presantation.add.adapter.DrinkViewPagerAdapter
import com.utmaximur.alcoholtracker.presantation.add.adapter.DrinkViewPagerAdapter.AddDrinkListener
import com.utmaximur.alcoholtracker.presantation.base.BaseViewModelFactory
import com.utmaximur.alcoholtracker.util.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


class AddFragment : Fragment(), AddDrinkListener {

    private var addFragmentListener: AddFragmentListener? = null

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory<AddViewModel>

    private val viewModel: AddViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    interface AddFragmentListener {
        fun onHideNavigationBar()
        fun onShowNavigationBar()
        fun onShowAddNewDrinkFragment()
        fun onShowEditNewDrinkFragment(bundle: Bundle)
        fun closeFragment()
    }

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var datePecker: DatePickerDialog
    private var dateAndTime = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDagger()
        setDrinksList()
        initUi()
    }

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    private fun initUi() {
        val navController = findNavController()

        binding.toolbar.setNavigationOnClickListener {
            hideKeyboard()
            addFragmentListener?.closeFragment()
        }

        binding.toolbar.setOnMenuItemClickListener {
            if (binding.viewPagerDrinks.currentItem != getDrinksList().size) {
                viewModel.onSaveButtonClick(
                    Track(
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
        binding.viewPagerIndicator.setupWithViewPager(binding.viewPagerDrinks, true)
        binding.viewPagerDrinks.addOnPageChangeListener(object : OnPageChangeListener {
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
        binding.quantityNumberPicker.maxValue = 10
        binding.quantityNumberPicker.minValue = 1
        binding.quantityNumberPicker.setOnScrollListener { _, _ ->
            if (viewModel.checkIsEmptyFieldPrice(getPrice())) {
                binding.totalMoneyText.text = viewModel.getTotalMoney(getQuantity(), getPrice())
            }
        }


        viewModel.drinksList.observe(viewLifecycleOwner, { list ->
            // Degree
            // set default first drink
            initNumberPicker(binding.degreeNumberPicker)
            binding.degreeNumberPicker.displayedValues = null
            binding.degreeNumberPicker.maxValue = list.first().degree.size - 1
            binding.degreeNumberPicker.displayedValues = list.first().degree.toTypedArray()
            binding.degreeNumberPicker.value = 1

            // Volume
            // set default first drink
            initNumberPicker(binding.volumeNumberPicker)
            binding.volumeNumberPicker.displayedValues = null
            binding.volumeNumberPicker.maxValue = list.first().volume.size - 1
            binding.volumeNumberPicker.displayedValues =
                list.first().volume.setVolumeUnit(requireContext()).toTypedArray()
            binding.volumeNumberPicker.value = 1
            setVolume(list.first().volume.toList())
        })

        binding.addDateButton.setOnClickListener {
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

        binding.todayButton.setOnClickListener {
            binding.addDateButton.text = Date().time.formatDate(requireContext())
            viewModel.date = Date().time
            binding.todayButton.toGone()
        }

        binding.eventEditText.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                return@OnEditorActionListener true
            }
            true
        })

        binding.priceEditText.setOnClickListener {
            val bundle = Bundle()
            bundle.putString(PRICE_DRINK, binding.priceEditText.text.toString())
            navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
                KEY_CALCULATOR
            )
                ?.observe(
                    viewLifecycleOwner
                ) { result ->
                    lifecycleScope.launch {
                        getValueCalculating(result)
                    }
                }
            navController.navigate(R.id.calculatorFragment, bundle)
        }
    }

    private fun hideKeyboard() {
        binding.eventEditText.hideKeyboard()
    }

    private fun setDrinkDegreeArray(position: Int) {
        val drink: Drink = getDrinksList()[position]
        binding.degreeNumberPicker.displayedValues = null
        binding.degreeNumberPicker.minValue = 0
        binding.degreeNumberPicker.value = 1
        binding.degreeNumberPicker.maxValue = drink.degree.indexOf(drink.degree.last())
        binding.degreeNumberPicker.displayedValues = drink.degree.toTypedArray()
        setDegreeList(drink.degree)
    }

    private fun setDrinkVolumeArray(position: Int) {
        val drink: Drink = getDrinksList()[position]
        binding.volumeNumberPicker.displayedValues = null
        binding.volumeNumberPicker.minValue = 0
        binding.volumeNumberPicker.value = 1
        binding.volumeNumberPicker.maxValue = drink.volume.indexOf(drink.volume.last())
        binding.volumeNumberPicker.displayedValues =
            drink.volume.setVolumeUnit(requireContext()).toTypedArray()
        setVolume(drink.volume)
    }

    private fun setEditArguments() {

        val track: Track? = requireArguments().getParcelable(DRINK)
        binding.toolbar.title = getString(R.string.edit_drink_title)
        viewModel.id = track?.id.toString()
        viewModel.date = track?.date!!

        viewModel.drinksList.observe(viewLifecycleOwner, { list ->
            list.forEach { drink ->
                if (drink.drink == track.drink) {
                    val position = list.indexOf(drink)
                    // set position view pager
                    binding.viewPagerDrinks.currentItem = position
                    binding.viewPagerIndicator.getTabAt(position)?.select()
                    // set quantity drink
                    binding.quantityNumberPicker.value = track.quantity
                    // set volume drink
                    setDrinkVolumeArray(position)
                    binding.volumeNumberPicker.value = drink.volume.indexOf(track.volume)
                    initNumberPicker(binding.volumeNumberPicker)
                    // set degree drink
                    setDrinkDegreeArray(position)
                    binding.degreeNumberPicker.value = drink.degree.indexOf(track.degree)
                    initNumberPicker(binding.degreeNumberPicker)
                }
            }
        })

        binding.eventEditText.setText(track.event)
        binding.priceEditText.setText(track.price.toString())
        binding.addDateButton.text = track.date.formatDate(requireContext())
        binding.todayButton.toGone()

        binding.totalMoneyText.text =
            (track.price.times(track.quantity)).toString()
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
            binding.addDateButton.text = dateAndTime.timeInMillis.formatDate(requireContext())
            viewModel.date = Date(dateAndTime.timeInMillis).time
            binding.todayButton.toGone()
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
        return getDrinksList()[binding.viewPagerDrinks.currentItem].drink
    }

    private fun getQuantity(): Int {
        return binding.quantityNumberPicker.value
    }

    private fun getDegree(): String {
        return binding.degreeNumberPicker.displayedValues[binding.degreeNumberPicker.value].toString()
    }

    private fun getEvent(): String {
        return binding.eventEditText.text.toString()
    }

    private fun getPrice(): Float {
        return if (binding.priceEditText.text.toString().isEmpty()) {
            viewModel.price
        } else {
            binding.priceEditText.text.toString().toFloat()
        }
    }

    private fun getDate(): Long {
        return viewModel.date
    }

    private fun getVolume(): String? {
        return getVolumeList()[binding.volumeNumberPicker.value]
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
        return getDrinksList()[binding.viewPagerDrinks.currentItem].icon
    }

    private fun setDrinksList() {
        viewModel.drinksList.observe(viewLifecycleOwner, { list ->
            viewModel.drinkDBOS = list
            val adapter = DrinkViewPagerAdapter(list, requireContext())
            adapter.setListener(this)
            binding.viewPagerDrinks.adapter = adapter
            binding.viewPagerDrinks.alphaView()
            if (arguments != null) {
                if (arguments?.containsKey(SELECT_DAY)!! && arguments?.getLong(SELECT_DAY) != 0L) {
                    val selectDate = requireArguments().getLong(SELECT_DAY)
                    binding.addDateButton.text = selectDate.formatDate(requireContext())
                    viewModel.date = selectDate
                    binding.todayButton.toGone()
                } else {
                    setEditArguments()
                }
            }
        })
    }

    private fun getDrinksList(): List<Drink> {
        return viewModel.drinkDBOS
    }

    private fun getValueCalculating(value: String) {
        binding.priceEditText.setText(value)
        if (value.isNotEmpty()) {
            binding.totalMoneyText.text =
                (binding.quantityNumberPicker.value * value.toInt()).toString()
        } else {
            binding.totalMoneyText.text = getString(R.string.add_empty)
        }
    }

    override fun addNewDrink() {
        addFragmentListener?.onShowAddNewDrinkFragment()
    }

    override fun deleteDrink(drink: Drink) {
        val navController = findNavController()
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>(KEY_ADD)
            ?.observe(
                viewLifecycleOwner
            ) { result ->
                if (result == KEY_ADD_OK) {
                    lifecycleScope.launch {
                        viewModel.deleteDrink(drink)
                    }
                }
            }
        navController.navigate(R.id.deleteDialogFragment)
    }

    override fun editDrink(drink: Drink) {
        val bundle = Bundle()
        bundle.putParcelable(EDIT_DRINK, drink)
        addFragmentListener?.onShowEditNewDrinkFragment(bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}