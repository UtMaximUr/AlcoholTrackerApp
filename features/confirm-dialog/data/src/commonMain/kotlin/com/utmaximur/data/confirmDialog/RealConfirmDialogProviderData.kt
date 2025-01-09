package com.utmaximur.data.confirmDialog

import com.utmaximur.app.base.BaseSingleProviderData
import com.utmaximur.app.base.MutableSharedFlowWrapper
import com.utmaximur.domain.confirmDialog.ConfirmDialogProviderData
import org.koin.core.annotation.Single


@Single
internal class RealConfirmDialogProviderData :
    BaseSingleProviderData<Long?>(MutableSharedFlowWrapper()), ConfirmDialogProviderData