package com.zekierciyas.cache.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class PersistentStorage constructor(
    private val context: Context
) : Storage {

    companion object {
        private val Context.datastore : DataStore< Preferences> by  preferencesDataStore(name = "satellites_base_data_storage")
        const val OPERATION_SUCCESS = "operation_success"
        const val OPERATION_FAILURE = "operation_failure"
    }

    override suspend fun insert(key: String, data: String): Flow<String> {
       return flow {
           runCatching {
               context.datastore.edit { preferences ->
                   val dataStoreKey = stringPreferencesKey(key)
                   preferences[dataStoreKey] = data
               }
           }.onFailure {
               emit(OPERATION_FAILURE)
               Timber.e("Inserting data to storage been failed due to error: $it")
           }.onSuccess {
               Timber.d("Inserting data to data storage has been succeed")
               emit(OPERATION_SUCCESS)
           }
        }
    }


    override suspend fun get(where: String): Flow<String?> {
        return flow {
            runCatching {
                val dataStoreKey = stringPreferencesKey(where)
                val preferences = context.datastore.data.first()
                emit(preferences[dataStoreKey])
                Timber.d("Getting data from storage has been succeed")
            }.onFailure {
                Timber.e("Getting data from storage has been failed due to error: $it")
                emit(OPERATION_FAILURE)
            }
        }
    }
}