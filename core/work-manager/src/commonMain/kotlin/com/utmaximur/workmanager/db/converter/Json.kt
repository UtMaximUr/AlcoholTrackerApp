package com.utmaximur.workmanager.db.converter

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import com.utmaximur.workmanager.db.entity.BooleanData
import com.utmaximur.workmanager.db.entity.DataEntity
import com.utmaximur.workmanager.db.entity.DoubleData
import com.utmaximur.workmanager.db.entity.FloatData
import com.utmaximur.workmanager.db.entity.IntData
import com.utmaximur.workmanager.db.entity.LongData
import com.utmaximur.workmanager.db.entity.StringData
import com.utmaximur.workmanager.db.entity.UnknownData

internal val json = Json {
    ignoreUnknownKeys = true
    serializersModule = SerializersModule {
        polymorphic(DataEntity::class) {
            subclass(IntData::class)
            subclass(LongData::class)
            subclass(DoubleData::class)
            subclass(FloatData::class)
            subclass(StringData::class)
            subclass(BooleanData::class)

            defaultDeserializer { UnknownData.serializer() }
        }
    }
}