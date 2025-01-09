package com.utmaximur.data.createDrink.mapper

import com.utmaximur.data.Mapper
import com.utmaximur.databaseRoom.icon.DbIcon
import com.utmaximur.domain.models.Icon
import org.koin.core.annotation.Factory

@Factory
internal class IconLocalMapper : Mapper<DbIcon, Icon> {
    override fun transform(from: DbIcon) = Icon(
        id = from.id,
        url = from.url
    )
}