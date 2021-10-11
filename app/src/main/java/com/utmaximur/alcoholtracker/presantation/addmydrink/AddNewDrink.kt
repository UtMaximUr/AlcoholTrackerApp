package com.utmaximur.alcoholtracker.presantation.addmydrink


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
import androidx.recyclerview.widget.ConcatAdapter
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.databinding.FragmentAddNewDrinkBinding
import com.utmaximur.alcoholtracker.domain.entity.Drink
import com.utmaximur.alcoholtracker.domain.entity.Icon
import com.utmaximur.alcoholtracker.presantation.addmydrink.adapter.SelectIconAdapter
import com.utmaximur.alcoholtracker.presantation.addmydrink.adapter.SelectVolumeAdapter
import com.utmaximur.alcoholtracker.presantation.base.BaseViewModelFactory
import com.utmaximur.alcoholtracker.util.*
import java.util.*
import javax.inject.Inject


class AddNewDrink : Fragment() {

    private var addNewFragmentListener: AddNewFragmentListener? = null

    interface AddNewFragmentListener {
        fun closeFragment()
    }

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory<AddNewDrinkViewModel>

    private val viewModel: AddNewDrinkViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    private var _binding: FragmentAddNewDrinkBinding? = null
    private val binding get() = _binding!!

    private var selectIconAdapter: SelectIconAdapter? = null
    private var iconConcatAdapter: ConcatAdapter? = null
    private var selectVolumeAdapter: SelectVolumeAdapter? = null
    private var volumeConcatAdapter: ConcatAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNewDrinkBinding.inflate(layoutInflater)
        injectDagger()
        initUi()
        return binding.root
    }

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    private fun initUi() {
        binding.toolbar.setNavigationOnClickListener {
            hideKeyboard()
            addNewFragmentListener?.closeFragment()
        }

        binding.toolbar.setOnMenuItemClickListener {
            if (viewModel.checkEmptyField(requireContext()).isEmpty()) {
                hideKeyboard()
                viewModel.onSaveButtonClick()
                addNewFragmentListener?.closeFragment()
            } else {
                view?.snackBar(viewModel.checkEmptyField(requireContext()))
            }
            true
        }

        binding.photoDrink.setOnClickListener {
            hideKeyboard()
            val navController = findNavController()
            navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>(KEY_CREATE_DRINK)?.observe(
                viewLifecycleOwner) { result ->
                if (result == KEY_CREATE_DRINK_DELETE) {
                    binding.photoDrink.setImageBitmap(null)
                    binding.photoDrink.setImageResource(R.drawable.ic_camera)
                    binding.photoDrink.scaleType = ImageView.ScaleType.CENTER
                } else {
                    binding.photoDrink.setImagePath(result)
                    binding.photoDrink.scaleType = ImageView.ScaleType.CENTER_CROP
                    viewModel.photo = result
                }
                navController.currentBackStackEntry?.savedStateHandle?.remove<String>(KEY_CREATE_DRINK)
            }
            navController.navigate(R.id.addPhotoBottomDialogFragment)
        }

        binding.drinkName.setOnEditorActionListener(TextView.OnEditorActionListener { text, actionId, _ ->
            viewModel.nameDrink = text.text.toString()
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard()
                return@OnEditorActionListener true
            }
            true
        })

        setIconAdapter(null)
        setVolumeAdapter(null)

        binding.minRangeDegree.text = binding.rangeDegree.getMin().toString()
        binding.maxRangeDegree.text = binding.rangeDegree.getMax().toString()

        binding.rangeDegree.addMaxRangeChangeListener {
            binding.maxRangeDegree.text = it.format1f()
            getDegree()
        }

        binding.rangeDegree.addMinRangeChangeListener {
            binding.minRangeDegree.text = it.format1f()
            getDegree()
        }

        if (arguments != null) {
            setArguments()
        }
    }

    private fun hideKeyboard() {
        binding.drinkName.hideKeyboard()
    }

    private fun setArguments() {
        val drink: Drink? = requireArguments().getParcelable(EDIT_DRINK)
        viewModel.id = drink?.id.toString()
        viewModel.photo = drink?.photo.toString()
        viewModel.nameDrink = drink?.drink.toString()
        viewModel.degreeList = drink?.degree as ArrayList<String?>
        viewModel.volumeList = drink.volume as ArrayList<String?>
        viewModel.icon = drink.icon

        if (drink.photo.isNotEmpty()) {
            binding.photoDrink.setImagePath(drink.photo)
        }
        binding.drinkName.setText(drink.drink)
        setIconAdapter(Icon(drink.icon))
        binding.rangeDegree.setCurrentRangeMin(drink.degree.first()?.toDouble()?.toFloat()!!)
        binding.rangeDegree.setCurrentRangeMax(drink.degree.last()?.toDouble()?.toFloat()!!)
        binding.minRangeDegree.text = drink.degree.first()
        binding.maxRangeDegree.text = drink.degree.last()

        setVolumeAdapter(drink.volume)
    }

    private fun setIconAdapter(icon: Icon?) {
        selectIconAdapter = SelectIconAdapter(this::adapterIconOnClick, icon)
        iconConcatAdapter = ConcatAdapter(selectIconAdapter)
        binding.drinkAddIcon.adapter = iconConcatAdapter
        selectIconAdapter?.submitList(viewModel.getIcons())
    }

    private fun setVolumeAdapter(volumes: List<String?>?) {
        selectVolumeAdapter = SelectVolumeAdapter(this::adapterVolumeOnClick, volumes)
        volumeConcatAdapter = ConcatAdapter(selectVolumeAdapter)
        binding.drinkAddVolume.adapter = volumeConcatAdapter
        selectVolumeAdapter?.submitList(viewModel.getVolumes(requireContext()))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        addNewFragmentListener = context as AddNewFragmentListener
    }

    private fun adapterIconOnClick(icon: Icon) {
        hideKeyboard()
        viewModel.icon = icon.icon
    }

    private fun adapterVolumeOnClick(volume: String?) {
        hideKeyboard()
        if (viewModel.volumeList.contains(volume)) {
            viewModel.volumeList.remove(volume)
        } else {
            viewModel.volumeList.add(volume)
        }
    }

    private fun getDegree(): List<String?> {
        return viewModel.getDoubleDegree(
            binding.rangeDegree.getCurrentRangeMin().toDouble(),
            binding.rangeDegree.getCurrentRangeMax().toInt() - binding.rangeDegree.getCurrentRangeMin().toInt()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
