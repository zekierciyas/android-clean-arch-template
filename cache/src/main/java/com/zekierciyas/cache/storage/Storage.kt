package com.zekierciyas.cache.storage

import kotlinx.coroutines.flow.Flow

interface Storage {

    suspend fun insert(key: String, data: String): Flow<String>

    suspend fun get(where: String): Flow<String?>

}