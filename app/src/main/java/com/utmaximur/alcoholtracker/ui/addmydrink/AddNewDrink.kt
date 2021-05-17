package com.utmaximur.alcoholtracker.ui.addmydrink


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
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.model.Icon
import com.utmaximur.alcoholtracker.databinding.FragmentAddNewDrinkBinding
import com.utmaximur.alcoholtracker.di.component.AlcoholTrackComponent
import com.utmaximur.alcoholtracker.di.factory.AddNewDrinkViewModelFactory
import com.utmaximur.alcoholtracker.ui.addmydrink.adapter.SelectIconAdapter
import com.utmaximur.alcoholtracker.ui.addmydrink.adapter.SelectVolumeAdapter
import com.utmaximur.alcoholtracker.util.*
import java.util.*


class AddNewDrink : Fragment() {

    private var addNewFragmentListener: AddNewFragmentListener? = null

    interface AddNewFragmentListener {
        fun closeFragment()
    }

    private lateinit var binding: FragmentAddNewDrinkBinding

    private var selectIconAdapter: SelectIconAdapter? = null
    private var iconConcatAdapter: ConcatAdapter? = null
    private var selectVolumeAdapter: SelectVolumeAdapter? = null
    private var volumeConcatAdapter: ConcatAdapter? = null

    private lateinit var viewModel: AddNewDrinkViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNewDrinkBinding.inflate(layoutInflater)
        injectDagger()
        initViewModel()
        initUi()
        return binding.root
    }

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    private fun initViewModel() {
        val dependencyFactory: AlcoholTrackComponent =
            (requireActivity().application as App).alcoholTrackComponent
        val drinkRepository = dependencyFactory.provideDrinkRepository()
        val iconRepository = dependencyFactory.provideAssetsRepository()
        val viewModel: AddNewDrinkViewModel by viewModels {
            AddNewDrinkViewModelFactory(drinkRepository, iconRepository)
        }
        this.viewModel = viewModel
    }

    private fun initUi() {
        binding.toolbar.setNavigationOnClickListener {
            hideKeyboard()
            addNewFragmentListener?.closeFragment()
        }

        binding.toolbar.setOnMenuItemClickListener {
            if (viewModel.checkEmptyField(requireContext()).isEmpty()) {
                hideKeyboard()
                viewModel.onSaveButtonClick(
                    Drink(
                        getIdDrink(),
                        getName(),
                        getDegree(),
                        getVolume(),
                        getIcon(),
                        getPhoto()
                    )
                )
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
//        setIconAdapter(drink.icon.getIdRaw(requireContext()).let { Icon(it) })
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

    private fun getIdDrink(): String {
        return viewModel.id
    }

    private fun getName(): String {
        return binding.drinkName.text.toString()
    }

    private fun getDegree(): List<String?> {
        return viewModel.getDoubleDegree(
            binding.rangeDegree.getCurrentRangeMin().toDouble(),
            binding.rangeDegree.getCurrentRangeMax().toInt() - binding.rangeDegree.getCurrentRangeMin().toInt()
        )
    }

    private fun getVolume(): List<String?> {
        return viewModel.volumeList
    }

    private fun getIcon(): String {
        return viewModel.icon
    }

    private fun getPhoto(): String {
        return viewModel.photo
    }
}
