package com.utmaximur.data.splash_screen.mapper

import com.utmaximur.data.Mapper
import com.utmaximur.data.splash_screen.network.IconRemote
import com.utmaximur.databaseRoom.icon.DbIcon
import org.koin.core.annotation.Factory

@Factory
internal class IconRemoteMapper : Mapper<IconRemote, DbIcon> {
    override fun transform(from: IconRemote) = DbIcon(
        id = from.id.toLong(),
        url = from.url
    )
}