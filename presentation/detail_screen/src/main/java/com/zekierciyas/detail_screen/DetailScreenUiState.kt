package com.zekierciyas.detail_screen

import com.zekierciyas.cache.model.detail.SatelliteDetail
import com.zekierciyas.cache.model.detail.SatelliteDetailItem
import com.zekierciyas.cache.model.satellite_list.SatelliteListItem

sealed class DetailScreenUiState{
    object Loading: DetailScreenUiState()
    data class Loaded(val detail : SatelliteDetailItem, val name: String): DetailScreenUiState()
    data class Error(val error: Throwable = Throwable()): DetailScreenUiState()
}