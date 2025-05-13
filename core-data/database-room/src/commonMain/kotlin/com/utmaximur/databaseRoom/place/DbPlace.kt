package com.utmaximur.databaseRoom.place

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.utmaximur.databaseRoom.track.DbTrack

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = DbTrack::class,
            parentColumns = ["id"],
            childColumns = ["trackId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["trackId"], unique = true)]
)

data class DbPlace(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val longitude: Double,
    val latitude: Double,
    val trackId: Long
)