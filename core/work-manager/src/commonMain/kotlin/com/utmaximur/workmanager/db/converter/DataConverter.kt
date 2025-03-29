package com.utmaximur.workmanager.db.converter

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.serialization.encodeToString
import com.utmaximur.workmanager.db.entity.DataEntity
import com.utmaximur.workmanager.db.entity.UnknownData
import com.utmaximur.workmanager.work.WorkerData
import com.utmaximur.workmanager.work.workData

@TypeConverters
internal class DataConverter {

    @TypeConverter
    fun typeFromJson(value: String): WorkerData {
        val list = json.decodeFromString<List<DataEntity>>(value)

        return workData {
            list.forEach { entity ->
                if (entity !is UnknownData)
                    put(entity.key, entity.value)
            }
        }
    }

    @TypeConverter
    fun typeToJson(data: WorkerData): String {
        val mapped: List<DataEntity> = data.map
            .map { entry ->
                DataEntity.create(entry.key, entry.value)
            }

        return json.encodeToString(mapped)
    }

}
