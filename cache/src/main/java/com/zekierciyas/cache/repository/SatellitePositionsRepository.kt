package com.zekierciyas.cache.repository

import android.content.Context
import com.zekierciyas.cache.getJsonDataFromAsset
import com.zekierciyas.cache.model.position.Positions
import com.zekierciyas.cache.storage.PersistentStorage
import com.zekierciyas.cache.toObject
import kotlinx.coroutines.flow.collectLatest

class SatellitePositionsRepository constructor(
    private val context: Context,
    private val storage: PersistentStorage
): SatellitePositions {

    companion object {
        private const val KEY = "satellite_positions"
        private const val ASSET_NAME = "satellite_positions.json"
    }

    override suspend fun getPositions(onComplete: (Positions) -> Unit, onError: () -> Unit) {
        storage.get(
            where = KEY
        ).collectLatest {
            when(it) {
                PersistentStorage.OPERATION_FAILURE -> {
                    onError.invoke()
                }

                else -> {
                    if(!it.isNullOrEmpty()) onComplete.invoke(it.toObject())
                    else {
                        //If data is null or empty We can accept that the data is not saved before,
                        //in this case the data can be fetched from assets only if it does not existing on storage.
                        val jsonData = context.getJsonDataFromAsset(ASSET_NAME)
                        saveSatellites(
                            data = jsonData!!,
                            onComplete = {
                                onComplete.invoke(jsonData.toObject())
                            },
                            onError = {
                                onError.invoke()
                            }
                        )
                    }
                }
            }
        }
    }

    /** Save the relevant data to local data storage, to use it for other requested times.
     * @param data : Data to write on local storage
     * @param onComplete : On success handling callback
     */
    private suspend fun saveSatellites(data: String, onComplete: () -> Unit, onError: () -> Unit) {
        storage.insert(
            key = KEY,
            data = data
        ).collectLatest {
            when(it) {
                PersistentStorage.OPERATION_SUCCESS -> {
                    onComplete.invoke()
                }

                PersistentStorage.OPERATION_FAILURE -> {
                    onError.invoke()
                }
            }
        }
    }
}