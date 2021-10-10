package com.utmaximur.alcoholtracker.presantation.dialog.addphoto

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.databinding.DialogBottomSheetBinding
import com.utmaximur.alcoholtracker.di.component.AlcoholTrackComponent
import com.utmaximur.alcoholtracker.di.factory.AddPhotoViewModelFactory
import com.utmaximur.alcoholtracker.util.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream

class AddPhotoBottomDialogFragment : BottomSheetDialogFragment() {

    private lateinit var viewModel: AddPhotoViewModel
    private lateinit var binding: DialogBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogBottomSheetBinding.inflate(inflater)
        initViewModel()
        initUi()
        return binding.root
    }

    private fun initViewModel() {
        val dependencyFactory: AlcoholTrackComponent =
            (requireActivity().application as App).alcoholTrackComponent
        val fileRepository = dependencyFactory.provideFileRepository()
        val viewModel: AddPhotoViewModel by viewModels {
            AddPhotoViewModelFactory(fileRepository)
        }
        this.viewModel = viewModel
    }

    private fun initUi() {
        binding.useCamera.setOnClickListener {
            if (checkPermissionsPhoto()) {
                openCamera()
            }
        }

        binding.loadGallery.setOnClickListener {
            if (checkPermissionsGallery()) {
                openGallery()
            }
        }

        binding.deletePhoto.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                KEY_CREATE_DRINK,
                KEY_CREATE_DRINK_DELETE
            )
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
                        findNavController().previousBackStackEntry?.savedStateHandle?.set(
                            KEY_CREATE_DRINK,
                            viewModel.savePhoto(requireContext(), selectedImage)
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
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    KEY_CREATE_DRINK,
                    viewModel.savePhoto(requireContext(), bitmap)
                )
                dialog?.dismiss()
            }
            val file: File = viewModel.getFile(requireContext(), viewModel.photoURI)!!
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                KEY_CREATE_DRINK,
                file.absolutePath
            )
            dialog?.dismiss()
            if (viewModel.photoFile!!.exists()) {
                viewModel.photoFile!!.delete()
            }
        }


    private fun openGallery() {
        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        intent.type = "image/"
        getResultGallery.launch(intent)
    }


    @SuppressLint("QueryPermissionsNeeded")
    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireContext().packageManager) != null) {
            viewModel.photoFile = null
            try {
                viewModel.photoFile = viewModel.getImageFile(requireContext())
            } catch (ex: IOException) {
            }
            if (viewModel.photoFile != null) {
                viewModel.photoURI = FileProvider.getUriForFile(
                    requireContext(),
                    requireContext().packageName + FILE_PROVIDER,
                    viewModel.photoFile!!
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, viewModel.photoURI)
                getResultCamera.launch(takePictureIntent)
                viewModel.photoFile?.deleteOnExit()
            }
        }
    }
}