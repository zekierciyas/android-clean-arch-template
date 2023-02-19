package com.zekierciyas.cache.repository.satellite_list

import com.zekierciyas.cache.model.satellite_list.SatelliteList

interface SatelliteList {

    suspend fun getSatellites(
        onComplete: (SatelliteList) -> Unit,
        onError: () -> Unit
    )
}