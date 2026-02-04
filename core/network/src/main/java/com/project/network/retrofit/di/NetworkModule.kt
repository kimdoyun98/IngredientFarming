package com.project.network.retrofit.di

import com.project.network.retrofit.BarcodeApiService
import com.project.network.retrofit.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofitClient(): BarcodeApiService = RetrofitClient.instance
}
