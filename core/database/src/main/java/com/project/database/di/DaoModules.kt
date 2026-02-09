package com.project.database.di

import com.project.database.IngredientFarmingDatabase
import com.project.database.dao.IngredientDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModules {

    @Singleton
    @Provides
    fun provideIngredientDao(
        database: IngredientFarmingDatabase
    ): IngredientDao = database.getIngredientDao()
}
