package com.utmaximur.alcoholtracker.presentation.create_my_drink


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.databinding.FragmentCreateDrinkBinding
import com.utmaximur.alcoholtracker.domain.entity.Icon
import com.utmaximur.alcoholtracker.presentation.create_my_drink.adapter.SelectIconAdapter
import com.utmaximur.alcoholtracker.presentation.create_my_drink.adapter.SelectVolumeAdapter
import com.utmaximur.alcoholtracker.presentation.base.BaseViewModelFactory
import com.utmaximur.alcoholtracker.util.*
import java.util.*
import javax.inject.Inject


class CreateMyDrink : Fragment() {

    private var addNewFragmentListener: AddNewFragmentListener? = null

    interface AddNewFragmentListener {
        fun closeFragment()
    }

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory<CreateMyDrinkViewModel>

    private val viewModel: CreateMyDrinkViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    private var _binding: FragmentCreateDrinkBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateDrinkBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDagger()
        initUi()
        initDrinkArguments()
    }

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    private fun initUi() = with(binding) {
        toolbar.setNavigationOnClickListener {
            hideKeyboard()
            addNewFragmentListener?.closeFragment()
        }

        toolbar.setOnMenuItemClickListener {
            if (viewModel.checkEmptyField(requireContext()).isEmpty()) {
                hideKeyboard()
                viewModel.onSaveButtonClick()
                addNewFragmentListener?.closeFragment()
            } else {
                view?.snackBar(viewModel.checkEmptyField(requireContext()))
            }
            true
        }

        photoDrink.setOnClickListener {
            hideKeyboard()
            this@CreateMyDrink.getNavigationResultLiveData<String>(KEY_CREATE_DRINK)?.observe(
                viewLifecycleOwner
            ) { result ->
                if (result == KEY_CREATE_DRINK_DELETE) {
                    photoDrink.setImageBitmap(null)
                    photoDrink.setImageResource(R.drawable.ic_camera)
                    photoDrink.scaleType = ImageView.ScaleType.CENTER
                } else {
                    photoDrink.setImagePath(result)
                    photoDrink.scaleType = ImageView.ScaleType.CENTER_CROP
                    viewModel.onPhotoChange(result)
                }
            }
            findNavController().navigate(R.id.addPhotoBottomDialogFragment)
        }

        drinkName.setOnEditorActionListener(TextView.OnEditorActionListener { text, actionId, _ ->
            viewModel.onNameDrinkChange(text.text.toString())
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                return@OnEditorActionListener true
            }
            true
        })

        initIconAdapter(null)
        initVolumeAdapter(null)

        minRangeDegree.text = rangeDegree.getMin().toString()
        maxRangeDegree.text = rangeDegree.getMax().toString()

        rangeDegree.addMaxRangeChangeListener {
            maxRangeDegree.text = it.format1f()
            initDegree()
        }

        rangeDegree.addMinRangeChangeListener {
            minRangeDegree.text = it.format1f()
            initDegree()
        }
    }

    private fun hideKeyboard() = with(binding) {
        drinkName.hideKeyboard()
    }

    private fun initDrinkArguments() = with(binding) {
        if (arguments != null) {
            viewModel.onDrinkChange(requireArguments().getParcelable(EDIT_DRINK))

            viewModel.drink.observe(viewLifecycleOwner, { drink ->
                if (drink?.photo?.isNotEmpty()!!) {
                    photoDrink.setImagePath(drink.photo)
                }
                drinkName.setText(drink.drink)
                initIconAdapter(Icon(drink.icon))
                rangeDegree.setCurrentRangeMin(drink.degree.first()?.toDouble()?.toFloat()!!)
                rangeDegree.setCurrentRangeMax(drink.degree.last()?.toDouble()?.toFloat()!!)
                minRangeDegree.text = drink.degree.first()
                maxRangeDegree.text = drink.degree.last()
                initVolumeAdapter(drink.volume)
            })
        }
    }

    private fun initIconAdapter(icon: Icon?) = with(binding) {
        val selectIconAdapter = SelectIconAdapter(this@CreateMyDrink::onClickIcon, icon)
        drinkAddIcon.adapter = selectIconAdapter
        selectIconAdapter.submitList(viewModel.getIcons())
    }

    private fun initVolumeAdapter(volumes: List<String?>?) = with(binding) {
        val selectVolumeAdapter = SelectVolumeAdapter(this@CreateMyDrink::onClickVolume, volumes)
        drinkAddVolume.adapter = selectVolumeAdapter
        selectVolumeAdapter.submitList(viewModel.getVolumes(requireContext()))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        addNewFragmentListener = context as AddNewFragmentListener
    }

    private fun onClickIcon(icon: Icon) {
        hideKeyboard()
        viewModel.onIconChange(icon.icon)
    }

    private fun onClickVolume(volume: String?) {
        hideKeyboard()
        viewModel.onVolumeChange(volume)
    }

    private fun initDegree() = with(binding) {
        viewModel.onDegreeChange(
            rangeDegree.getCurrentRangeMin(),
            rangeDegree.getCurrentRangeMax()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
