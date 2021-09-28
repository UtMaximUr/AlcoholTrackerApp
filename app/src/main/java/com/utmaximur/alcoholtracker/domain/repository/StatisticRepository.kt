package com.utmaximur.alcoholtracker.domain.repository


import com.utmaximur.alcoholtracker.domain.entity.TrackStatistic

interface StatisticRepository {

    suspend fun getStatistics(): TrackStatistic

}