package com.zekierciyas.list_screen.di

import com.zekierciyas.list_screen.adapter.ListScreenAdapter
import com.zekierciyas.list_screen.search.CachedSearchingEngine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideCachedSearchingEngine(): CachedSearchingEngine = CachedSearchingEngine().cacheSize(5)

    @Provides
    @Singleton
    fun provideListScreenAdapter(searchingEngine: CachedSearchingEngine): ListScreenAdapter = ListScreenAdapter(searchingEngine)
}