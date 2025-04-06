package com.utmaximur.media

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUUID
import platform.Foundation.NSUserDomainMask
import platform.Foundation.writeToURL
import platform.UIKit.UIApplication
import platform.UIKit.UIImage
import platform.UIKit.UIImageJPEGRepresentation
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerCameraCaptureMode.UIImagePickerControllerCameraCaptureModePhoto
import platform.UIKit.UIImagePickerControllerDelegateProtocol
import platform.UIKit.UIImagePickerControllerOriginalImage
import platform.UIKit.UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
import platform.UIKit.UINavigationControllerDelegateProtocol
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberCameraManager(onResult: (PlatformFile) -> Unit): CameraManager {
    val imagePicker = UIImagePickerController()
    val cameraDelegate = remember {
        object :
            NSObject(),
            UIImagePickerControllerDelegateProtocol,
            UINavigationControllerDelegateProtocol {
            override fun imagePickerController(
                picker: UIImagePickerController,
                didFinishPickingMediaWithInfo: Map<Any?, *>
            ) {
                val didFinishPickingImage =
                    didFinishPickingMediaWithInfo[UIImagePickerControllerOriginalImage] as UIImage

                picker.dismissViewControllerAnimated(true) {
                    val data = UIImageJPEGRepresentation(didFinishPickingImage, 1.0)
                    val fileName = NSUUID.UUID().UUIDString + ".jpg"
                    val fileURL = NSFileManager.defaultManager
                        .URLForDirectory(
                            directory = NSDocumentDirectory,
                            inDomain = NSUserDomainMask,
                            appropriateForURL = null,
                            create = true,
                            error = null
                        )?.URLByAppendingPathComponent(fileName)
                    fileURL?.let { nsUrl ->
                        data?.writeToURL(nsUrl, atomically = true)
                        onResult.invoke(PlatformFile(nsUrl))
                    }
                }
            }
        }
    }
    return remember {
        CameraManager {
            if (UIImagePickerController.isSourceTypeAvailable(UIImagePickerControllerSourceTypeCamera)) {
                imagePicker.setSourceType(UIImagePickerControllerSourceTypeCamera)
                imagePicker.setCameraCaptureMode(UIImagePickerControllerCameraCaptureModePhoto)
            }
            imagePicker.setAllowsEditing(false)
            imagePicker.setDelegate(cameraDelegate)
            UIApplication.sharedApplication.keyWindow?.rootViewController?.presentViewController(
                imagePicker,
                true,
                null
            )
        }
    }
}