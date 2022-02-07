package com.utmaximur.alcoholtracker.presentation.create_track

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Surface
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.presentation.base.BaseViewModelFactory
import com.utmaximur.alcoholtracker.presentation.create_track.ui.CreateTrackerView
import com.utmaximur.alcoholtracker.presentation.splash.ui.theme.AlcoholTrackerTheme
import com.utmaximur.alcoholtracker.util.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class CreateTrackFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory<CreateTrackViewModel>

    private val viewModel: CreateTrackViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    private var addFragmentListener: AddFragmentListener? = null
    private val dateAndTime = Calendar.getInstance()
    private lateinit var datePicker: DatePickerDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injectDagger()
        return ComposeView(requireContext()).apply {
            setContent {
                AlcoholTrackerTheme {
                    Surface(color = colorResource(id = R.color.background_color)) {
                        CreateTrackerView(viewModel = viewModel)
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTrackArguments()
        initObservers()
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
        findNavController().graph.findNode(R.id.addFragment)?.removeArgument(SELECT_DAY_ADD)
        addFragmentListener?.onShowNavigationBar()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        addFragmentListener = context as AddFragmentListener
    }

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    private fun initObservers() = with(viewModel) {
        closeState.observe(viewLifecycleOwner) {
            addFragmentListener?.closeFragment()
        }
        createDrinkState.observe(viewLifecycleOwner) {
            addFragmentListener?.onShowAddNewDrinkFragment()
        }
        saveState.observe(viewLifecycleOwner) {
            onSaveResult()
        }
        calculateState.observe(viewLifecycleOwner) { price ->
            this@CreateTrackFragment.onCalculateClick(price)
        }
        editDrinkState.observe(viewLifecycleOwner) { drink ->
            val bundle = Bundle()
            bundle.putParcelable(EDIT_DRINK, drink)
            addFragmentListener?.onShowEditNewDrinkFragment(bundle)
        }
        deleteDrinkState.observe(viewLifecycleOwner) { drink ->
            this@CreateTrackFragment.getNavigationResultLiveData<String>(KEY_ADD)
                ?.observe(
                    viewLifecycleOwner
                ) { result ->
                    if (result == KEY_ADD_OK) {
                        lifecycleScope.launch {
                            onDeleteDrink(drink)
                        }
                    }
                }
            findNavController().navigate(R.id.deleteDialogFragment)
        }
        selectDayState.observe(viewLifecycleOwner) { date ->
            dateAndTime.timeInMillis = date ?: Date().time
            datePicker = DatePickerDialog(
                requireContext(), { _, year, monthOfYear, dayOfMonth ->
                    dateAndTime[Calendar.YEAR] = year
                    dateAndTime[Calendar.MONTH] = monthOfYear
                    dateAndTime[Calendar.DAY_OF_MONTH] = dayOfMonth
                    viewModel.onDateChange(
                        Date(dateAndTime.timeInMillis).time,
                        dateAndTime.timeInMillis.formatDate(requireContext())
                    )
                },
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }
        selectTodayState.observe(viewLifecycleOwner) {
            viewModel.onDateChange(
                Date().time,
                Date().time.formatDate(requireContext())
            )
        }
    }

    private fun onSaveResult() {
        this@CreateTrackFragment.setNavigationResult(
            KEY_CALENDAR_UPDATE,
            KEY_CALENDAR_UPDATE_OK,
            false
        )
    }

    private fun onCalculateClick(price: String) {
        val bundle = Bundle()
        bundle.putString(PRICE_DRINK, price)
        this@CreateTrackFragment.getNavigationResultLiveData<String>(KEY_CALCULATOR)
            ?.observe(
                viewLifecycleOwner
            ) { result ->
                lifecycleScope.launch {
                    viewModel.onTotalMoneyCalculating(result)
                }
            }
        findNavController().navigate(R.id.calculatorFragment, bundle)
    }

    private fun initTrackArguments() {
        if (arguments != null) {
            if (requireArguments().containsKey(SELECT_DAY_ADD)) {
                val selectDate: Long = requireArguments().get(SELECT_DAY_ADD) as Long
                viewModel.onDateChange(selectDate)
            } else {
                viewModel.onTitleChange(R.string.edit_drink_title)
                viewModel.onTrackChange(requireArguments().getParcelable(DRINK))
            }
        }
    }

    interface AddFragmentListener {
        fun onHideNavigationBar()
        fun onShowNavigationBar()
        fun onShowAddNewDrinkFragment()
        fun onShowEditNewDrinkFragment(bundle: Bundle)
        fun closeFragment()
    }
}