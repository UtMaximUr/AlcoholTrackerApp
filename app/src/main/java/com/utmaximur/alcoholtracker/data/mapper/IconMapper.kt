package com.utmaximur.alcoholtracker.data.mapper


import com.utmaximur.alcoholtracker.data.dbo.IconDBO
import com.utmaximur.alcoholtracker.domain.entity.Icon


class IconMapper {

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