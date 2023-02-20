package com.zekierciyas.list_screen.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zekierciyas.cache.repository.satellite_list.SatelliteListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val satelliteListRepository: SatelliteListRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ListScreenUiState>(ListScreenUiState.Loading)
    val uiState: StateFlow<ListScreenUiState> = _uiState

    init {
        viewModelScope.launch {
            delay(500) //Mock delay to see progress loader
            satelliteListRepository.getSatellites(
                onComplete = {
                    Timber.d("Data is successfully taken")
                    _uiState.value = ListScreenUiState.Loaded(it)
                },

                onError = {
                    Timber.e("An error occurred")
                    _uiState.value = ListScreenUiState.Error(Throwable("Un-known error"))
                }
            )
        }
    }
}