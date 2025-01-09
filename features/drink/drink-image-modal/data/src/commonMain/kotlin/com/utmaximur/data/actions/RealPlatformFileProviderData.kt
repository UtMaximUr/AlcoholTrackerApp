package com.utmaximur.data.actions

import com.utmaximur.app.base.BaseSingleProviderData
import com.utmaximur.app.base.MutableSharedFlowWrapper
import com.utmaximur.domain.actions.PlatformFileProviderData
import org.koin.core.annotation.Single


@Single
internal class RealPlatformFileProviderData :
    BaseSingleProviderData<String>(MutableSharedFlowWrapper()), PlatformFileProviderData