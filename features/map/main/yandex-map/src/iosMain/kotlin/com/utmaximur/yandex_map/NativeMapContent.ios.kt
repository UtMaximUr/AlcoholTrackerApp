package com.utmaximur.yandex_map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitInteropInteractionMode
import androidx.compose.ui.viewinterop.UIKitInteropProperties
import androidx.compose.ui.viewinterop.UIKitView
import com.utmaximur.domain.map.PlaceMark
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.CGRectMake
import platform.UIKit.UIView

@OptIn(ExperimentalComposeUiApi::class, ExperimentalForeignApi::class)
@Composable
internal actual fun NativeMapContent(
    places: List<PlaceMark>,
    yandexMapController: YandexMapController
) {
    UIKitView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            UIView(frame = CGRectMake(.0, .0, .0, .0)).apply {
                addSubview(yandexMapController.getView())
            }
        },
        update = {
            yandexMapController.submitData(places)
        },
        onRelease = {
            yandexMapController.onStop()
        },
        properties = UIKitInteropProperties(
            interactionMode = UIKitInteropInteractionMode.NonCooperative,
            isNativeAccessibilityEnabled = true
        )
    )
}