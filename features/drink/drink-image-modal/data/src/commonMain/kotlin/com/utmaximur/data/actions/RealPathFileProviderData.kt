package com.utmaximur.data.actions

import com.utmaximur.app.base.BaseSingleProviderData
import com.utmaximur.app.base.MutableSharedFlowWrapper
import com.utmaximur.domain.actions.PathFileProviderData
import org.koin.core.annotation.Single

@Single
internal class RealPathFileProviderData :
    BaseSingleProviderData<String>(MutableSharedFlowWrapper()), PathFileProviderData
