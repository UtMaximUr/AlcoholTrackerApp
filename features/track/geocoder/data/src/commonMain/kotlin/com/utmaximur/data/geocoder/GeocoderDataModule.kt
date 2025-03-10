package com.utmaximur.data.geocoder

import com.utmaximur.data.geocoder.network.GeocoderApi
import de.jensklingenberg.ktorfit.Ktorfit
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan
class GeocoderDataModule {
    @Single
    fun provideGeocoderApi(ktorfit: Ktorfit): GeocoderApi = ktorfit.create()
}
