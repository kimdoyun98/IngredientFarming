package com.project.ingredient.di

import com.project.ingredient.repository.DefaultIngredientSettingRepository
import com.project.ingredient.repository.DefaultIngredientSettingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class InitSettingsModule {
    @Binds
    abstract fun bindDefaultIngredientSettingRepository(
        defaultIngredientSettingRepositoryImpl: DefaultIngredientSettingRepositoryImpl
    ): DefaultIngredientSettingRepository
}
