package com.zekierciyas.cache.di

import android.content.Context
import com.zekierciyas.cache.repository.satellite_detail.SatelliteDetailRepository
import com.zekierciyas.cache.storage.PersistentStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CacheModule {

    @Provides
    @Singleton
    fun provideSatelliteDetailRepository(@ApplicationContext context: Context, storage : PersistentStorage): SatelliteDetailRepository {
        return SatelliteDetailRepository(
            context = context,
            storage
        )
    }

    @Provides
    @Singleton
    fun providePersistentStorage(@ApplicationContext context: Context): PersistentStorage {
        return PersistentStorage(
            context = context
        )
    }
}
