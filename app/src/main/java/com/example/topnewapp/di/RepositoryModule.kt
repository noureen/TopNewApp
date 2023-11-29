package com.example.topnewapp.di

import HeadlineRepositoryImpl
import com.example.topnewapp.api.HeadlineApiService
import com.example.topnewapp.repository.HeadlineRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideHeadlineRepository(apiService: HeadlineApiService): HeadlineRepository {
        return HeadlineRepositoryImpl(apiService)
    }
}

