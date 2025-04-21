package com.utmaximur.data.kandinsky.mapper

import com.utmaximur.app.base.app.ApplicationInfo
import com.utmaximur.data.Mapper
import com.utmaximur.data.kandinsky.network.models.ImageStyleRemote
import com.utmaximur.domain.kandinsky.ImageStyle
import org.koin.core.annotation.Factory

private const val RU_LANGUAGE = "ru_RU"

@Factory
internal class ImageStyleDomainMapper(
    private val applicationInfo: ApplicationInfo
) : Mapper<ImageStyleRemote, ImageStyle> {
    override fun transform(from: ImageStyleRemote) = ImageStyle(
        styleImageUrl = from.image.orEmpty(),
        title = when (applicationInfo.language) {
            RU_LANGUAGE -> from.title.orEmpty()
            else -> from.titleEn.orEmpty()
        },
        name = from.name.orEmpty()
    )
}