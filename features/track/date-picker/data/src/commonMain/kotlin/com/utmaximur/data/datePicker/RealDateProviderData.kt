package com.utmaximur.data.datePicker

import com.utmaximur.app.base.BaseSingleProviderData
import com.utmaximur.app.base.MutableSharedFlowWrapper
import com.utmaximur.domain.datePicker.DateProviderData
import org.koin.core.annotation.Single


@Single
internal class RealDateProviderData :
    BaseSingleProviderData<Long?>(MutableSharedFlowWrapper()), DateProviderData