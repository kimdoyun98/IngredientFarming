package com.project.shopping_cart.di

import com.project.shopping_cart.repository.ShoppingCartRepository
import com.project.shopping_cart.repository.ShoppingCartRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ShoppingCartModule {
    @Binds
    abstract fun bindIngredientRepository(
        shoppingCartRepositoryImpl: ShoppingCartRepositoryImpl
    ): ShoppingCartRepository
}
