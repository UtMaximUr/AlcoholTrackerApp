package com.utmaximur.workmanager.constraint

import com.utmaximur.workmanager.dsl.CommonConstraints

/**
 * Use to check multiple constraint for a given worker
 *
 * TLDR, will not be used on Android, where everything will be passed to WorkManager
 */
internal interface ConstraintCheck {

    suspend fun match(commonConstraints: CommonConstraints): Boolean

}

internal suspend fun List<ConstraintCheck>.match(commonConstraints: CommonConstraints): Boolean {
    return all { it.match(commonConstraints) }
}