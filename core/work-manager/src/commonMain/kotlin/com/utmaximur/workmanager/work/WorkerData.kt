package com.utmaximur.workmanager.work

/**
 * Class use to store input or output data work for [Worker]
 */
data class WorkerData(
    internal val map: Map<String, Any?>
) {

    /**
     * Get a [T] for given key
     *
     * @return a [T]
     */
    @DelicateApi
    inline fun <reified T : Any> get(key: String): T? {
        return getDataValue(key)
    }

    /**
     * Get a data value for given key
     * @return a [T]
     */
    @DelicateApi
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getDataValue(key: String): T? {
        return map[key] as? T
    }

    /**
     * Get a [Int] for given key
     *
     * @return a [Int] for the given key or null if not found or wrong type
     */
    fun getInt(key: String, defaultValue: Int): Int = map[key]?.toString()
        ?.toIntOrNull() ?: defaultValue

    /**
     * Get a [Long] for given key
     *
     * @return a [Long] for the given key or null if not found or wrong type
     */
    fun getLong(key: String, defaultValue: Long): Long = map[key]?.toString()
        ?.toLongOrNull() ?: defaultValue

    /**
     * Get a [Float] for given key
     *
     * @return a [Float] for the given key or null if not found or wrong type
     */
    fun getFloat(key: String, defaultValue: Float): Float = map[key]?.toString()
        ?.toFloatOrNull() ?: defaultValue

    /**
     * Get a [Double] for given key
     *
     * @return a [Double] for the given key or null if not found or wrong type
     */
    fun getDouble(key: String, defaultValue: Double): Double = map[key]?.toString()
        ?.toDoubleOrNull() ?: defaultValue

    /**
     * Get a [String] for given key
     *
     * @return a [String] for the given key or null if not found or wrong type
     */
    fun getString(key: String, defaultValue: String): String = map[key]?.toString() ?: defaultValue

    /**
     * Get a [Boolean] for given key
     *
     * @return a [Boolean] for the given key or null if not found or wrong type
     */
    fun getBoolean(key: String, defaultValue: Boolean): Boolean = map[key]?.toString()
        ?.toBooleanStrictOrNull() ?: defaultValue

}

fun dataOf(vararg arg: Pair<String, Any?>): WorkerData {
    val definition = DataDefinition()

    arg.forEach {
        definition.put(it.first, it.second)
    }

    return definition.build()
}

fun workData(block: DataDefinition.() -> Unit): WorkerData {
    return DataDefinition().apply(block)
        .build()
}

class DataDefinition internal constructor() {

    private val map: MutableMap<String, Any?> = mutableMapOf()

    fun put(key: String, value: Any?) {
        map[key] = value
    }

    internal fun build(): WorkerData {
        return WorkerData(map)
    }

}

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class DelicateApi