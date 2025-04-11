package com.utmaximur.yandex_map.resources

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.yandex.runtime.ui_view.ViewProvider
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

internal actual class MapIconProvider actual constructor(
    private val placeMarkImageProvider: ImageProvider,
    private val clusterImageProvider: ImageProvider,
    private val clusterTextColor: Color
) : KoinComponent {

    private val clusterBackground by lazy { clusterImageProvider.toNative().image }
    private val context: Context = get()

    actual fun getClusterIcon(clusterSize: Int): MapIcon {
        val textView = TextView(context).apply {
            text = "$clusterSize"
            gravity = Gravity.CENTER
            setTextColor(clusterTextColor.toArgb())
            background = BitmapDrawable(context.resources, clusterBackground)
        }
        return MapIcon(ViewProvider(textView))
    }

    actual fun getIcon(): MapIcon {
        val image = placeMarkImageProvider.toNative().image
        val imageView = ImageView(context)
        imageView.setImageBitmap(image)
        return MapIcon(ViewProvider(imageView))
    }
}

internal actual class MapIcon(viewProvider: ViewProvider) {
    val native: ViewProvider = viewProvider
}