package com.utmaximur.workmanager.models

/**
 * An enumeration of the conflict resolution policies available for LorraineRequest
 * in case of conflict
 */
enum class ExistingWorkerPolicy {
    /**
     * Append request, do not replace if the previous one with the same uniqueId
     */
    APPEND,

    /**
     * Append request, remove previous request with the same uniqueId
     */
    APPEND_OR_REPLACE,

    /**
     *
     */
    REPLACE,

    /**
     *
     */
    KEEP
}