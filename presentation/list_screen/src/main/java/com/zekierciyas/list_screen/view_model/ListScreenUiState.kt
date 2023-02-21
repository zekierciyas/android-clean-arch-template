package com.zekierciyas.list_screen.view_model

import com.zekierciyas.cache.model.satellite_list.SatelliteListItem

sealed class ListScreenUiState{
    object Loading: ListScreenUiState()
    data class Loaded(val list : List<SatelliteListItem>): ListScreenUiState()
    data class Error(val error: Throwable): ListScreenUiState()
}




