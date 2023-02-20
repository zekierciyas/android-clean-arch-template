package com.zekierciyas.cache.repository.satellite_detail

import android.content.Context
import com.zekierciyas.cache.getJsonDataFromAsset
import com.zekierciyas.cache.storage.PersistentStorage
import com.zekierciyas.cache.toObject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class SatelliteDetailRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val storage: PersistentStorage
): SatelliteDetail {

    companion object {
        private const val KEY = "satellite_detail"
        private const val ASSET_NAME = "satellite_detail.json"
    }

    override suspend fun getDetails(onComplete: (com.zekierciyas.cache.model.detail.SatelliteDetail) -> Unit, onError: () -> Unit) {
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
                        saveSatelliteDetails(
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
    private suspend fun saveSatelliteDetails(data: String, onComplete: () -> Unit, onError: () -> Unit) {
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