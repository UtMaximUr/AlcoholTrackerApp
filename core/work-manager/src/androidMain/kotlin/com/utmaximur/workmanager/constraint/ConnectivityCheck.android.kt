package com.utmaximur.workmanager.constraint

import com.utmaximur.workmanager.dsl.CommonConstraints

/**
 * Is not used, since WorkManager handle his own check on connectivity
 */
internal actual object ConnectivityCheck : ConstraintCheck {

    actual override suspend fun match(commonConstraints: CommonConstraints): Boolean {
        return true
    }

}
