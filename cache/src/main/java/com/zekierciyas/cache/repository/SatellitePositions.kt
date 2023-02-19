package com.zekierciyas.cache.repository

import com.zekierciyas.cache.model.position.Positions

interface SatellitePositions {

    suspend fun getPositions(
        onComplete: (Positions) -> Unit,
        onError: () -> Unit
    )
}