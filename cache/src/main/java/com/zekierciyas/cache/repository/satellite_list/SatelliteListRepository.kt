package com.zekierciyas.cache.repository.satellite_list

import android.content.Context
import com.zekierciyas.cache.getJsonDataFromAsset
import com.zekierciyas.cache.storage.PersistentStorage
import com.zekierciyas.cache.toObject
import kotlinx.coroutines.flow.collectLatest

class SatelliteListRepository constructor(
    private val context: Context,
    private val storage: PersistentStorage): SatelliteList {

    companion object {
        private const val KEY = "satellite_list"
        private const val ASSET_NAME = "satellite_list.json"
    }

    /** Fetches the requested data from data storage, if the requested data is not existing on the
     * data storage gets data from the local assets file and saves it to data storage. If the app
     * caches is not deleted, second try the data will be fetched from data storage.
     *
     * @param onComplete returns data as [SatelliteList]
     * @param onError An error handling callback
     */
    override suspend fun getSatellites(
        onComplete: (com.zekierciyas.cache.model.satellite_list.SatelliteList) -> Unit,
        onError: () -> Unit
    ) {
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