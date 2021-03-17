package com.utmaximur.alcoholtracker.ui.addmydrink.view

import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.dagger.component.AlcoholTrackComponent
import com.utmaximur.alcoholtracker.dagger.factory.AddNewDrinkViewModelFactory
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.ui.customview.RangeSeekBar
import com.utmaximur.alcoholtracker.ui.dialog.addicon.AddIconDrinkDialogFragment
import com.utmaximur.alcoholtracker.ui.dialog.addicon.AddIconDrinkDialogFragment.*
import com.utmaximur.alcoholtracker.ui.dialog.addphoto.AddPhotoBottomDialogFragment
import com.utmaximur.alcoholtracker.ui.dialog.addphoto.AddPhotoBottomDialogFragment.BottomDialogListener
import com.utmaximur.alcoholtracker.ui.dialog.addvolume.AddVolumeDrinkDialogFragment
import com.utmaximur.alcoholtracker.ui.dialog.addvolume.AddVolumeDrinkDialogFragment.*


class AddNewDrink : Fragment(), BottomDialogListener,
    SelectIconListener,
    SelectVolumeListener {

    private var addNewFragmentListener: AddNewFragmentListener? = null

    interface AddNewFragmentListener {
        fun closeFragment()
    }

    private lateinit var toolbar: Toolbar
    private lateinit var photo: ImageView
    private lateinit var nameDrink: EditText
    private lateinit var addIcon: LinearLayout
    private lateinit var iconDrink: ImageView
    private lateinit var minValueDegree: TextView
    private lateinit var maxValueDegree: TextView
    private lateinit var rangeDegree: RangeSeekBar
    private lateinit var minValueVolume: TextView
    private lateinit var maxValueVolume: TextView
    private lateinit var addVolume: LinearLayout

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
        val viewModel: AddNewDrinkViewModel by viewModels {
            AddNewDrinkViewModelFactory(drinkRepository)
        }
        this.viewModel = viewModel
    }

    private fun findViewById(view: View) {

        toolbar = view.findViewById(R.id.toolbar)
        photo = view.findViewById(R.id.photo_drink)
        nameDrink = view.findViewById(R.id.drink_name_text)
        addIcon = view.findViewById(R.id.drink_add_icon)
        iconDrink = view.findViewById(R.id.drink_icon_image)
        minValueDegree = view.findViewById(R.id.left_range_degree)
        maxValueDegree = view.findViewById(R.id.right_range_degree)
        rangeDegree = view.findViewById(R.id.range_seek_bar_degree)
        minValueVolume = view.findViewById(R.id.left_range_volume)
        maxValueVolume = view.findViewById(R.id.right_range_volume)
        addVolume = view.findViewById(R.id.layout_volume)

    }

    private fun initUi(view: View) {
        findViewById(view)

        toolbar.setNavigationOnClickListener {
            addNewFragmentListener?.closeFragment()
        }

        toolbar.setOnMenuItemClickListener {
            viewModel.onSaveButtonClick(
                Drink(
                    "",
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
            val addPhotoBottomDialogFragment =
                AddPhotoBottomDialogFragment()
            addPhotoBottomDialogFragment.setListener(this)
            addPhotoBottomDialogFragment.show(
                requireActivity().supportFragmentManager,
                "add_photo_dialog_fragment"
            )
        }

        addIcon.setOnClickListener {
            val addIconDrinkDialogFragment =
                AddIconDrinkDialogFragment()
            addIconDrinkDialogFragment.setListener(this)
            addIconDrinkDialogFragment.show(
                requireActivity().supportFragmentManager,
                "add_icon_dialog_fragment"
            )
        }

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

        addVolume.setOnClickListener {
            val addVolumeDrinkDialogFragment =
                AddVolumeDrinkDialogFragment()
            addVolumeDrinkDialogFragment.setListener(this)
            addVolumeDrinkDialogFragment.show(
                requireActivity().supportFragmentManager,
                "add_volume_dialog_fragment"
            )
        }

        if (arguments != null) {
            setArguments()
        }
    }

    private fun setArguments() {
        val drink: Drink? = requireArguments().getParcelable("editDrink")
        viewModel.id = drink?.id!!

        if (drink.photo != "") {
            Glide.with(requireContext()).load(drink.photo).into(photo)
        }
        nameDrink.setText(drink.drink)
        Glide.with(requireContext()).load(
            requireContext().resources.getIdentifier(
                drink.icon,
                "raw",
                requireContext().packageName
            )
        ).into(iconDrink)
        rangeDegree.setCurrentRangeMin(drink.degree.first()?.toDouble()?.toFloat()!!)
        rangeDegree.setCurrentRangeMax(drink.degree.last()?.toDouble()?.toFloat()!!)
        minValueDegree.text = drink.degree.first()
        maxValueDegree.text = drink.degree.last()
        minValueVolume.text = drink.volume.first()
        maxValueVolume.text = drink.volume.last()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        addNewFragmentListener = context as AddNewFragmentListener
    }

    override fun setImageViewPhoto(bitmap: Bitmap) {
        photo.setImageBitmap(bitmap)
        photo.scaleType = ImageView.ScaleType.CENTER_CROP
    }

    override fun deleteImageViewPhoto() {
        photo.setImageBitmap(null)
        photo.setImageResource(R.drawable.ic_camera)
        photo.scaleType = ImageView.ScaleType.CENTER
    }

    override fun selectIcon(icon: Int) {
        Glide.with(requireContext()).load(icon).into(iconDrink)
        viewModel.icon = requireContext().resources.getResourceName(icon)
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

    override fun selectVolumeList(volumeList: List<String>) {
        viewModel.volumeList = volumeList
        minValueVolume.text = volumeList.first()
        maxValueVolume.text = volumeList.last()
    }
}
