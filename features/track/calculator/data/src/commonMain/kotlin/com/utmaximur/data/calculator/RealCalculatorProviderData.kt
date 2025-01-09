package com.utmaximur.data.calculator

import com.utmaximur.app.base.BaseSingleProviderData
import com.utmaximur.app.base.MutableSharedFlowWrapper
import com.utmaximur.domain.calculator.CalculatorProviderData
import org.koin.core.annotation.Single


@Single
internal class RealCalculatorProviderData :
    BaseSingleProviderData<Float?>(MutableSharedFlowWrapper()), CalculatorProviderData