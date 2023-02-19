package com.zekierciyas.cache.repository.satellite_detail

import com.zekierciyas.cache.model.detail.SatelliteDetail

interface SatelliteDetail {

    suspend fun getDetails(
        onComplete: (SatelliteDetail) -> Unit,
        onError: () -> Unit
    )
}