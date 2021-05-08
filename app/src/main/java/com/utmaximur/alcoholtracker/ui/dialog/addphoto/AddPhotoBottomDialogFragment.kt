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
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.utmaximur.alcoholtracker.App
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.di.component.AlcoholTrackComponent
import com.utmaximur.alcoholtracker.di.factory.AddPhotoViewModelFactory
import com.utmaximur.alcoholtracker.util.*
import java.io.*
import java.util.*

class AddPhotoBottomDialogFragment(
    private val setImageViewPhoto: (String) -> Unit,
    private val deleteImageViewPhoto: () -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var useCamera: TextView
    private lateinit var loadGallery: TextView
    private lateinit var deletePhoto: TextView

    private lateinit var viewModel: AddPhotoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View = inflater.inflate(
            R.layout.dialog_bottom_sheet, container,
            false
        )
        initViewModel()
        initUi(view)
        return view
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

    private fun findViewById(view: View) {
        useCamera = view.findViewById(R.id.tv_btn_add_photo_camera)
        loadGallery = view.findViewById(R.id.tv_btn_add_photo_gallery)
        deletePhoto = view.findViewById(R.id.tv_btn_remove_photo)
    }

    private fun initUi(view: View) {
        findViewById(view)

        useCamera.setOnClickListener {
            checkPermissionsPhoto()
        }

        loadGallery.setOnClickListener {
            checkPermissionsGallery()
        }

        deletePhoto.setOnClickListener {
            deleteImageViewPhoto()
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
                    setImageViewPhoto(savePhoto(bitmap))
                    dialog?.dismiss()
                }
                val file: File = viewModel.getFile(requireContext(), viewModel.photoURI)!!
                setImageViewPhoto(file.absolutePath)
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
                        setImageViewPhoto(savePhoto(selectedImage))
                        dialog?.dismiss()
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun savePhoto(bitmap: Bitmap): String {
        val file = File(requireContext().filesDir, Date().time.toString() + FORMAT_IMAGE)
        try {
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos)
            } finally {
                fos?.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file.absolutePath
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