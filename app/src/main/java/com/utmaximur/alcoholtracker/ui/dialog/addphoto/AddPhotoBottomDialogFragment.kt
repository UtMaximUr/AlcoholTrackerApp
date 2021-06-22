package com.utmaximur.alcoholtracker.ui.dialog.addphoto

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
import androidx.core.app.ActivityCompat
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
            checkPermissionsPhoto()
        }

        binding.loadGallery.setOnClickListener {
            checkPermissionsGallery()
        }

        binding.deletePhoto.setOnClickListener {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                KEY_CREATE_DRINK,
                KEY_CREATE_DRINK_DELETE
            )
            dialog?.dismiss()
        }
    }

    private fun checkPermissionsPhoto() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                REQUEST_PHOTO
            )
        }
    }

    private fun checkPermissionsGallery() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openGallery()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_GALLERY
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PHOTO -> openCamera()
            REQUEST_GALLERY -> openGallery()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_PHOTO -> {
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
            REQUEST_GALLERY -> {
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
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/"
        startActivityForResult(intent, REQUEST_GALLERY)
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
                startActivityForResult(takePictureIntent, REQUEST_PHOTO)
                viewModel.photoFile?.deleteOnExit()
            }
        }
    }
}