package com.utmaximur.domain.repository

import com.utmaximur.domain.entity.Icon

interface AssetsRepository {
    fun getIcons(): List<Icon>
}