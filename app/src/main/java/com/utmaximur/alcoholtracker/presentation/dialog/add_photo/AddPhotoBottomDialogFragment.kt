package com.utmaximur.alcoholtracker.presentation.dialog.add_photo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.databinding.DialogAddPhotoBinding
import com.utmaximur.alcoholtracker.presentation.base.BaseViewModelFactory
import com.utmaximur.alcoholtracker.util.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

class AddPhotoBottomDialogFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: BaseViewModelFactory<AddPhotoViewModel>

    private val viewModel: AddPhotoViewModel by viewModels(
        factoryProducer = { viewModelFactory }
    )

    private var _binding: DialogAddPhotoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddPhotoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDagger()
        initUi()
    }

    private fun injectDagger() {
        App.instance.alcoholTrackComponent.inject(this)
    }

    private fun initUi() = with(binding) {
        useCamera.setOnClickListener {
            if (checkPermissionsPhoto()) {
                openCamera()
            }
        }

        loadGallery.setOnClickListener {
            if (checkPermissionsGallery()) {
                openGallery()
            }
        }

        deletePhoto.setOnClickListener {
            this@AddPhotoBottomDialogFragment.setNavigationResult(KEY_CREATE_DRINK,
                KEY_CREATE_DRINK_DELETE, false)
            dialog?.dismiss()
        }
    }

    private fun checkPermissionsPhoto(): Boolean {
        if (requireContext().let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.CAMERA
                )
            } != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermissions.launch(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            )
        } else {
            return true
        }
        return false
    }

    private val requestCameraPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.CAMERA] == true
            ) {
                openCamera()
            }
        }

    private fun checkPermissionsGallery(): Boolean {
        if (requireContext().let {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            } != PackageManager.PERMISSION_GRANTED) {
            requestGalleryPermissions.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            )
        } else {
            return true
        }
        return false
    }

    private val requestGalleryPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.CAMERA] == true
            ) {
                openGallery()
            }
        }

    private val getResultGallery =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            val data = it.data
            val resultCode = it.resultCode

            if (resultCode == Activity.RESULT_OK && data !== null) {
                try {
                    val imageUri: Uri = data.data!!
                    val imageStream: InputStream =
                        requireActivity().contentResolver.openInputStream(imageUri)!!
                    val selectedImage = BitmapFactory.decodeStream(imageStream)
                    if (selectedImage != null) {
                        this.setNavigationResult(
                            KEY_CREATE_DRINK,
                            viewModel.savePhoto(selectedImage), false
                        )
                    }
                    dialog?.dismiss()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }

    private val getResultCamera =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            val data = it.data
            val resultCode = it.resultCode

            if (resultCode == Activity.RESULT_OK && data !== null) {
                val bitmap = data.extras?.get(DATA) as Bitmap
                this.setNavigationResult(
                    KEY_CREATE_DRINK,
                    viewModel.savePhoto(bitmap), false
                )
                dialog?.dismiss()
            }
            viewModel.photoURI.observe(viewLifecycleOwner, { uri ->
                val file: File = viewModel.getFile(uri)!!
                this.setNavigationResult(
                    KEY_CREATE_DRINK,
                    file.absolutePath, false
                )
                dialog?.dismiss()
                viewModel.deleteFile()
            })
        }


    private fun openGallery() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        getResultGallery.launch(intent)
    }


    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireContext().packageManager) != null) {
            viewModel.clearFile()
            try {
                viewModel.updateImageFile()
            } catch (ex: IOException) {
            }
            viewModel.photoFile.observe(viewLifecycleOwner, { file ->
                viewModel.updatePhotoUri(
                    FileProvider.getUriForFile(
                        requireContext(),
                        requireContext().packageName + FILE_PROVIDER,
                        file
                    )
                )
            })
            viewModel.photoURI.observe(viewLifecycleOwner, { uri ->
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                getResultCamera.launch(takePictureIntent)
                viewModel.deleteFile()
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}