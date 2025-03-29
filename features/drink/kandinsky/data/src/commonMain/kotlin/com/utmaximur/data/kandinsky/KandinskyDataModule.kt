package com.utmaximur.data.kandinsky

import com.utmaximur.data.kandinsky.network.FusionBrainApi
import com.utmaximur.data.kandinsky.network.createFusionBrainApi
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single


@Module
@ComponentScan
class KandinskyDataModule {
    @Single
    fun provideFusionBrainApi(ktorfit: Ktorfit): FusionBrainApi = ktorfit.createFusionBrainApi()
}