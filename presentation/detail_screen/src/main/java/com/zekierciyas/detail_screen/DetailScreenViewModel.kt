package com.zekierciyas.detail_screen

import androidx.lifecycle.ViewModel
import com.zekierciyas.cache.model.position.Position
import com.zekierciyas.cache.model.position.Positions
import com.zekierciyas.cache.repository.SatellitePositionsRepository
import com.zekierciyas.cache.repository.satellite_detail.SatelliteDetailRepository
import com.zekierciyas.navigation.SatelliteListArgModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val positionsRepository: SatellitePositionsRepository,
    private val detailRepository: SatelliteDetailRepository
): ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    private val _uiState = MutableStateFlow<DetailScreenUiState>(DetailScreenUiState.Loading)
    val uiState: StateFlow<DetailScreenUiState> = _uiState

    private val _positions = MutableStateFlow<Position?>(null)
    val positions: StateFlow<Position?> = _positions

    private var selectedID = 0
    private var arg: SatelliteListArgModel? = null

    companion object {
        const val POSITION_CHANGE_DELAY : Long = 3000
    }

    init {
        viewModelScope.launch {
            positionsRepository.getPositions(
                onComplete = {
                    changePositionRandomly(it)
                },

                onError = {
                    _uiState.value = DetailScreenUiState.Error()
                }
            )

            detailRepository.getDetails(
                onComplete = {
                    _uiState.value = DetailScreenUiState.Loaded(it[selectedID], arg!!.name)
                },

                onError = {
                    _uiState.value = DetailScreenUiState.Error()
                }
            )
        }
    }

    private fun changePositionRandomly(positions: Positions) {
        val scope = CoroutineScope(Dispatchers.Main)
        scope.launch {
            var counter = 1
            while (true) {
                positions.list[selectedID].positions.random().also {
                    _positions.value = Position(it.posX,it.posY)
                }
                delay(POSITION_CHANGE_DELAY) // wait for 3 seconds
                counter++
            }
        }
    }

    fun setData(arg: SatelliteListArgModel) {
        selectedID = arg.id - 1
        this.arg = arg
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}