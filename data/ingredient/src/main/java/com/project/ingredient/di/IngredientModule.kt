package com.project.ingredient.di

import com.project.ingredient.repository.IngredientRepository
import com.project.ingredient.repository.IngredientRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class IngredientModule {

    @Binds
    abstract fun bindIngredientRepository(
        ingredientRepositoryImpl: IngredientRepositoryImpl
    ): IngredientRepository
}
