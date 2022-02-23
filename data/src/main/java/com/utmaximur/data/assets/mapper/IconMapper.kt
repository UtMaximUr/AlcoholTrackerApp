package com.utmaximur.data.assets.mapper

import com.utmaximur.data.local_data_source.dbo.IconDBO
import com.utmaximur.domain.entity.Icon
import javax.inject.Inject

class IconMapper @Inject constructor() {

    fun map(dbo: IconDBO): Icon {
        return Icon(
            icon = dbo.icon
        )
    }

    fun map(domain: Icon): IconDBO {
        return IconDBO(
            icon = domain.icon
        )
    }
}