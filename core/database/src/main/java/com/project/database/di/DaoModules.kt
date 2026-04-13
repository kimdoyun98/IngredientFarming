package com.project.database.di

import com.project.database.IngredientFarmingDatabase
import com.project.database.dao.CategoryGroupDao
import com.project.database.dao.HoldIngredientDao
import com.project.database.dao.IngredientDao
import com.project.database.dao.ShoppingCartDao
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

    @Singleton
    @Provides
    fun provideHoldIngredientDao(
        database: IngredientFarmingDatabase
    ): HoldIngredientDao = database.getHoldIngredientDao()

    @Singleton
    @Provides
    fun provideShoppingCartDao(
        database: IngredientFarmingDatabase
    ): ShoppingCartDao = database.getShoppingCardDao()

    @Singleton
    @Provides
    fun provideDefaultIngredientSettingDao(
        database: IngredientFarmingDatabase
    ): CategoryGroupDao = database.getCategoryGroupDao()
}
