package com.utmaximur.workmanager.constraint

import com.utmaximur.workmanager.dsl.CommonConstraints

internal expect object ConnectivityCheck : ConstraintCheck {

    override suspend fun match(commonConstraints: CommonConstraints): Boolean

}
