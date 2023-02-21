package com.zekierciyas.detail_screen.view_model

import com.zekierciyas.cache.model.detail.SatelliteDetailItem

sealed class DetailScreenUiState{
    object Loading: DetailScreenUiState()
    data class Loaded(val detail : SatelliteDetailItem, val name: String): DetailScreenUiState()
    data class Error(val error: Throwable = Throwable()): DetailScreenUiState()
}