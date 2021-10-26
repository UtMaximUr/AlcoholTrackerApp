package com.utmaximur.alcoholtracker.presentation.create_track

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.databinding.FragmentAddTrackBinding
import com.utmaximur.alcoholtracker.domain.entity.Drink
import com.utmaximur.alcoholtracker.presentation.create_track.adapter.DrinkViewPagerAdapter.AddDrinkListener
import com.utmaximur.alcoholtracker.presentation.base.BaseViewModelFactory
import com.utmaximur.alcoholtracker.presentation.create_track.adapter.DrinkViewPagerAdapter
import com.utmaximur.alcoholtracker.util.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CreateTrackFragment : Fragment(), AddDrinkListener {

    private var addFragmentListener: AddFragmentListener? = null

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory<CreateTrackViewModel>

    private val viewModel: CreateTrackViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    interface AddFragmentListener {
        fun onHideNavigationBar()
        fun onShowNavigationBar()
        fun onShowAddNewDrinkFragment()
        fun onShowEditNewDrinkFragment(bundle: Bundle)
        fun closeFragment()
    }

    private var _binding: FragmentAddTrackBinding? = null
    private val binding get() = _binding!!

    private lateinit var datePicker: DatePickerDialog
    private var dateAndTime = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTrackBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDagger()
        initUi()
        initDrinksList()
        initTrackArguments()
    }

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    private fun initUi() = with(binding) {
        toolbar.setNavigationOnClickListener {
            hideKeyboard()
            addFragmentListener?.closeFragment()
        }

        toolbar.setOnMenuItemClickListener {
            if (viewPagerDrinks.currentItem != viewModel.getDrinkList().size) {
                viewModel.onSaveButtonClick()
                this@CreateTrackFragment.setNavigationResult(
                    KEY_CALENDAR_UPDATE,
                    KEY_CALENDAR_UPDATE_OK,
                    false
                )
            }
            addFragmentListener?.closeFragment()
            true
        }

        // select drink
        viewPagerIndicator.setupWithViewPager(viewPagerDrinks, true)
        viewPagerDrinks.addOnPageChangeListener({ position ->
            if (viewModel.getDrinkList().size != position) {
                onDrinkDegreeArrayChange(position)
                onDrinkVolumeArrayChange(position)
                viewModel.onViewPagerPositionChange(position)
            }
        }, {
            hideKeyboard()
        })

        // Quantity
        quantityNumberPicker.settingsNumberPicker()
        quantityNumberPicker.setOnScrollListener { _, _ ->
            if (viewModel.checkIsEmptyFieldPrice(viewModel.getPrice())) {
                viewModel.getTotalMoney(viewModel.getQuantity(), viewModel.getPrice())
            }
        }
        viewModel.totalMoney.observe(viewLifecycleOwner, { total ->
            totalMoneyText.text = total
        })
        quantityNumberPicker.setOnValueChangedListener { numberPicker, _, _ ->
            viewModel.onQuantityChange(numberPicker.value)
        }

        viewModel.drinksList.observe(viewLifecycleOwner, { list ->
            // Degree
            // set default first drink
            degreeNumberPicker.settingsNumberPicker(
                list.first().degree.size,
                list.first().degree.toTypedArray()
            )
            degreeNumberPicker.setOnValueChangedListener { numberPicker, _, i ->
                viewModel.onDegreeChange(numberPicker.displayedValues[i].toString())
            }

            // Volume
            // set default first drink
            volumeNumberPicker.settingsNumberPicker(
                list.first().volume.size,
                list.first().volume.setVolumeUnit(requireContext()).toTypedArray()
            )
            volumeNumberPicker.setOnValueChangedListener { numberPicker, _, i ->
                viewModel.onVolumeChange(numberPicker.displayedValues[i].digitOnly(requireContext()))
            }
        })

        addDateButton.setOnClickListener { onShowDatePicker() }
        todayButton.setOnClickListener { onSelectDate() }

        eventEditText.onEditorActionListener { hideKeyboard() }
        eventEditText.doAfterTextChanged { viewModel.onEventChange(it?.toString()!!) }

        priceEditText.setOnClickListener { onShowCalculate() }

        priceEditText.doAfterTextChanged {
            if (it?.toString()?.isNotEmpty()!!) {
                viewModel.onPriceChange(it.toString().toFloat())
            }
        }
        viewModel.valueCalculating.observe(viewLifecycleOwner, { value ->
            priceEditText.setText(value)
        })
    }

    private fun onShowDatePicker() = with(binding) {
        if (viewModel.getDate() != 0L) {
            dateAndTime.timeInMillis = viewModel.getDate()
        }
        datePicker = DatePickerDialog(
            requireContext(), { _, year, monthOfYear, dayOfMonth ->
                dateAndTime[Calendar.YEAR] = year
                dateAndTime[Calendar.MONTH] = monthOfYear
                dateAndTime[Calendar.DAY_OF_MONTH] = dayOfMonth
                viewModel.onDateChange(dateAndTime.timeInMillis)
                addDateButton.text = dateAndTime.timeInMillis.formatDate(requireContext())
                viewModel.onDateChange(Date(dateAndTime.timeInMillis).time)
                todayButton.toGone()
            },
            dateAndTime.get(Calendar.YEAR),
            dateAndTime.get(Calendar.MONTH),
            dateAndTime.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun onSelectDate() = with(binding) {
        addDateButton.text = Date().time.formatDate(requireContext())
        viewModel.onDateChange(Date().time)
        todayButton.toGone()
    }

    private fun onShowCalculate() {
        val bundle = Bundle()
        bundle.putString(PRICE_DRINK, binding.priceEditText.text.toString())
        this@CreateTrackFragment.getNavigationResultLiveData<String>(KEY_CALCULATOR)
            ?.observe(
                viewLifecycleOwner
            ) { result ->
                lifecycleScope.launch {
                    viewModel.onValueCalculating(result)
                }
            }
        findNavController().navigate(R.id.calculatorFragment, bundle)
    }

    private fun hideKeyboard() = with(binding) {
        eventEditText.hideKeyboard()
    }

    private fun onDrinkDegreeArrayChange(position: Int) = with(binding) {
        val drink = viewModel.getDrinkList()[position]
        degreeNumberPicker.resetSettingsNumberPicker(
            drink.degree.indexOf(drink.degree.last()),
            drink.degree.toTypedArray()
        )
        viewModel.setDegreeList(drink.degree)
    }

    private fun onDrinkVolumeArrayChange(position: Int) = with(binding) {
        val drink = viewModel.getDrinkList()[position]
        volumeNumberPicker.resetSettingsNumberPicker(
            drink.volume.indexOf(drink.volume.last()),
            drink.volume.setVolumeUnit(requireContext()).toTypedArray()
        )
    }

    private fun initTrackArguments() = with(binding) {
        if (arguments != null) {
            if (requireArguments().containsKey(SELECT_DAY_ADD)) {
                val selectDate: Long = requireArguments().get(SELECT_DAY_ADD) as Long
                addDateButton.text = selectDate.formatDate(requireContext())
                viewModel.onDateChange(selectDate)
                todayButton.toGone()
            } else {
                setEditArguments()
            }
        }
    }

    private fun setEditArguments() = with(binding) {
        toolbar.title = getString(R.string.edit_drink_title)
        viewModel.onTrackChange(requireArguments().getParcelable(DRINK))

        viewModel.track.observe(viewLifecycleOwner, { track ->
            viewModel.drinksList.observe(viewLifecycleOwner, { list ->
                list.forEach { drink ->
                    if (drink.drink == track.drink) {
                        val position = list.indexOf(drink)
                        // set position view pager
                        viewPagerDrinks.currentItem = position
                        viewPagerIndicator.getTabAt(position)?.select()
                        // set quantity drink
                        quantityNumberPicker.value = track.quantity
                        // set volume drink
                        volumeNumberPicker.resetSettingsNumberPicker(
                            drink.volume.indexOf(drink.volume.last()),
                            drink.volume.setVolumeUnit(requireContext()).toTypedArray()
                        )
                        volumeNumberPicker.value = drink.volume.indexOf(track.volume)
                        // set degree drink
                        degreeNumberPicker.resetSettingsNumberPicker(
                            drink.degree.indexOf(drink.degree.last()),
                            drink.degree.toTypedArray()
                        )
                        degreeNumberPicker.value = drink.degree.indexOf(track.degree)
                    }
                }
            })

            eventEditText.setText(track.event)
            priceEditText.setText(track.price.toString())
            addDateButton.text = track.date.formatDate(requireContext())
            todayButton.toGone()
        })
    }

    private fun initDrinksList() = with(binding) {
        viewModel.drinksList.observe(viewLifecycleOwner, { list ->
            val adapter = DrinkViewPagerAdapter(list, requireContext())
            adapter.setListener(this@CreateTrackFragment)
            viewPagerDrinks.adapter = adapter
            viewPagerDrinks.alphaView()
            adapter.notifyDataSetChanged()
        })
    }

    override fun addNewDrink() {
        addFragmentListener?.onShowAddNewDrinkFragment()
    }

    override fun deleteDrink(drink: Drink) = with(binding) {
        this@CreateTrackFragment.getNavigationResultLiveData<String>(KEY_ADD)
            ?.observe(
                viewLifecycleOwner
            ) { result ->
                if (result == KEY_ADD_OK) {
                    lifecycleScope.launch {
                        viewModel.onDeleteDrink(drink)
                        viewPagerDrinks.adapter?.notifyDataSetChanged()
                    }
                }
            }
        findNavController().navigate(R.id.deleteDialogFragment)
    }

    override fun editDrink(drink: Drink) {
        val bundle = Bundle()
        bundle.putParcelable(EDIT_DRINK, drink)
        addFragmentListener?.onShowEditNewDrinkFragment(bundle)
    }

    override fun onStart() {
        super.onStart()
        addFragmentListener!!.onHideNavigationBar()
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateDrinks()
    }

    override fun onDestroy() {
        super.onDestroy()
        addFragmentListener?.onShowNavigationBar()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        addFragmentListener = context as AddFragmentListener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        findNavController().graph.findNode(R.id.addFragment)?.removeArgument(SELECT_DAY_ADD)
    }
}