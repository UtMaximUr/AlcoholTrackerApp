package com.utmaximur.alcoholtracker.ui.addmydrink.view


import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.dagger.component.AlcoholTrackComponent
import com.utmaximur.alcoholtracker.dagger.factory.AddNewDrinkViewModelFactory
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.model.Icon
import com.utmaximur.alcoholtracker.ui.addmydrink.view.adapter.SelectIconAdapter
import com.utmaximur.alcoholtracker.ui.addmydrink.view.adapter.SelectVolumeAdapter
import com.utmaximur.alcoholtracker.ui.customview.RangeSeekBar
import com.utmaximur.alcoholtracker.ui.dialog.addphoto.AddPhotoBottomDialogFragment
import com.utmaximur.alcoholtracker.ui.dialog.addphoto.AddPhotoBottomDialogFragment.BottomDialogListener
import java.util.ArrayList


class AddNewDrink : Fragment(), BottomDialogListener{

    private var addNewFragmentListener: AddNewFragmentListener? = null

    interface AddNewFragmentListener {
        fun closeFragment()
    }

    private lateinit var toolbar: Toolbar
    private lateinit var photo: ImageView
    private lateinit var nameDrink: EditText
    private lateinit var iconsList: RecyclerView
    private lateinit var volumeList: RecyclerView

    private var selectIconAdapter: SelectIconAdapter? = null
    private var iconConcatAdapter: ConcatAdapter? = null
    private var selectVolumeAdapter: SelectVolumeAdapter? = null
    private var volumeConcatAdapter: ConcatAdapter? = null

    private lateinit var minValueDegree: TextView
    private lateinit var maxValueDegree: TextView
    private lateinit var rangeDegree: RangeSeekBar

    private lateinit var viewModel: AddNewDrinkViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_add_new_drink, container, false)
        injectDagger()
        initViewModel()
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
        val iconRepository = dependencyFactory.provideIconRepository()
        val viewModel: AddNewDrinkViewModel by viewModels {
            AddNewDrinkViewModelFactory(drinkRepository, iconRepository)
        }
        this.viewModel = viewModel
    }

    private fun findViewById(view: View) {

        toolbar = view.findViewById(R.id.toolbar)
        photo = view.findViewById(R.id.photo_drink)
        nameDrink = view.findViewById(R.id.drink_name_text)
        iconsList = view.findViewById(R.id.drink_add_icon)
        volumeList = view.findViewById(R.id.drink_add_volume)
        minValueDegree = view.findViewById(R.id.left_range_degree)
        maxValueDegree = view.findViewById(R.id.right_range_degree)
        rangeDegree = view.findViewById(R.id.range_seek_bar_degree)

    }

    private fun initUi(view: View) {
        findViewById(view)

        toolbar.setNavigationOnClickListener {
            hideKeyboard()
            addNewFragmentListener?.closeFragment()
        }

        toolbar.setOnMenuItemClickListener {
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
            true
        }

        photo.setOnClickListener {
            hideKeyboard()
            val addPhotoBottomDialogFragment =
                AddPhotoBottomDialogFragment()
            addPhotoBottomDialogFragment.setListener(this)
            addPhotoBottomDialogFragment.show(
                requireActivity().supportFragmentManager,
                "add_photo_dialog_fragment"
            )
        }

        nameDrink.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, event ->
            if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN))) {
                hideKeyboard()
                return@OnEditorActionListener true
            }
            true
        })

        setIconAdapter(null)
        setVolumeAdapter(null)

        minValueDegree.text = rangeDegree.getMin().toString()
        maxValueDegree.text = rangeDegree.getMax().toString()

        rangeDegree.addMaxRangeChangeListener {
            val format: String = String.format("%.1f", it)
            maxValueDegree.text = format
        }

        rangeDegree.addMinRangeChangeListener {
            val format: String = String.format("%.1f", it)
            minValueDegree.text = format
        }

        if (arguments != null) {
            setArguments()
        }
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager = nameDrink.context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(nameDrink.windowToken, 0)
        nameDrink.clearFocus()
    }

    private fun setArguments() {
        val drink: Drink? = requireArguments().getParcelable("editDrink")
        viewModel.id = drink?.id.toString()
        viewModel.volumeList = drink?.volume as ArrayList<String?>
        viewModel.icon = drink.icon

        if (drink.photo != "") {
            Glide.with(requireContext()).load(drink.photo).into(photo)
        }
        nameDrink.setText(drink.drink)
        setIconAdapter(Icon(requireContext().resources.getIdentifier(
            drink.icon,
            "raw",
            requireContext().packageName
        )))
        rangeDegree.setCurrentRangeMin(drink.degree.first()?.toDouble()?.toFloat()!!)
        rangeDegree.setCurrentRangeMax(drink.degree.last()?.toDouble()?.toFloat()!!)
        minValueDegree.text = drink.degree.first()
        maxValueDegree.text = drink.degree.last()

        setVolumeAdapter(drink.volume)
    }

    private fun setIconAdapter(icon: Icon?) {
        selectIconAdapter = SelectIconAdapter(this::adapterIconOnClick, icon)
        iconConcatAdapter = ConcatAdapter(selectIconAdapter)
        iconsList.adapter = iconConcatAdapter
        viewModel.getIcons().observe(viewLifecycleOwner, {
            it?.let {
                selectIconAdapter?.submitList(it as MutableList<Icon>)
            }
        })
    }

    private fun setVolumeAdapter(volumes: List<String?>?){
        selectVolumeAdapter = SelectVolumeAdapter(this::adapterVolumeOnClick, volumes)
        volumeConcatAdapter = ConcatAdapter(selectVolumeAdapter)
        volumeList.adapter = volumeConcatAdapter
        selectVolumeAdapter?.submitList(viewModel.getVolumes(requireContext()))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        addNewFragmentListener = context as AddNewFragmentListener
    }

    override fun setImageViewPhoto(path: String) {
        Glide.with(requireContext()).load(path).into(photo)
        photo.scaleType = ImageView.ScaleType.CENTER_CROP
        viewModel.photo = path
    }

    override fun deleteImageViewPhoto() {
        photo.setImageBitmap(null)
        photo.setImageResource(R.drawable.ic_camera)
        photo.scaleType = ImageView.ScaleType.CENTER
    }

    private fun adapterIconOnClick(icon: Icon) {
        hideKeyboard()
        viewModel.icon = requireContext().resources.getResourceName(icon.icon)
    }

    private fun adapterVolumeOnClick(volume: String?) {
        hideKeyboard()
        if (viewModel.volumeList.contains(volume)){
            viewModel.volumeList.remove(volume)
        } else {
            viewModel.volumeList.add(volume)
        }
    }

    private fun getIdDrink(): String {
        return viewModel.id
    }

    private fun getName(): String {
        return nameDrink.text.toString()
    }

    private fun getDegree(): List<String?> {
        return viewModel.getDoubleDegree(
            rangeDegree.getCurrentRangeMin().toDouble(),
            rangeDegree.getCurrentRangeMax().toInt() - rangeDegree.getCurrentRangeMin().toInt()
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
