package com.utmaximur.data.kandinsky.mapper

import org.koin.core.annotation.Factory

@Factory
internal class MapperHolder(
    val imageStyleDomainMapper: ImageStyleDomainMapper,
    val generationResultDomainMapper: GenerationResultDomainMapper
)
