package com.project.ingredient.di

import com.project.ingredient.BarcodeInfoRepositoryImpl
import com.project.ingredient.repository.BarcodeInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BarcodeModule {

    @Binds
    abstract fun bindBarcodeInfoRepository(
        barcodeInfoRepositoryImpl: BarcodeInfoRepositoryImpl
    ): BarcodeInfoRepository
}
