package com.utmaximur.data.kandinsky.generate_image

import com.utmaximur.app.base.BaseSingleProviderData
import com.utmaximur.app.base.MutableSharedFlowWrapper
import com.utmaximur.data.kandinsky.network.models.GenerateResult
import org.koin.core.annotation.Single

@Single
internal class GenerateResultDataSource :
    BaseSingleProviderData<GenerateResult>(MutableSharedFlowWrapper())