package com.utmaximur.workmanager.db.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient


@Serializable
@SerialName("type")
internal sealed interface DataEntity {
    val key: String
    val value: Any

    companion object {
        internal fun <T> create(key: String, value: T): DataEntity = when (value) {
            is Int -> IntData(key = key, value = value)
            is Long -> LongData(key = key, value = value)
            is Float -> FloatData(key = key, value = value)
            is Double -> DoubleData(key = key, value = value)
            is String -> StringData(key = key, value = value)
            is Boolean -> BooleanData(key = key, value = value)
            is IntArray -> IntArrayData(key = key, value = value)

            else -> throw IllegalArgumentException("Unknown type")
        }
    }
}

@Serializable
@SerialName("int")
internal data class IntData(
    override val key: String,
    override val value: Int
) : DataEntity

@Serializable
@SerialName("long")
internal data class LongData(
    override val key: String,
    override val value: Long
) : DataEntity

@Serializable
@SerialName("double")
internal data class DoubleData(
    override val key: String,
    override val value: Double
) : DataEntity

@Serializable
@SerialName("float")
internal data class FloatData(
    override val key: String,
    override val value: Float
) : DataEntity

@Serializable
@SerialName("string")
internal data class StringData(
    override val key: String,
    override val value: String
) : DataEntity

@Serializable
@SerialName("boolean")
internal data class BooleanData(
    override val key: String,
    override val value: Boolean
) : DataEntity

@Serializable
@SerialName("intArray")
internal data class IntArrayData(
    override val key: String,
    override val value: IntArray
) : DataEntity {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as com.utmaximur.workmanager.db.entity.IntArrayData

        if (key != other.key) return false
        if (!value.contentEquals(other.value)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + value.contentHashCode()
        return result
    }
}

@Serializable
internal data object UnknownData : DataEntity {
    @Transient
    override val key: String = ""

    @Transient
    override val value: Any = Any()
}