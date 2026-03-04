package com.project.shopping_cart.repository

import com.project.model.cart.ShoppingCart
import kotlinx.coroutines.flow.Flow

interface ShoppingCartRepository {
    suspend fun insertShoppingCartItem(item: ShoppingCart)
    fun getAllShoppingCartItems(): Flow<List<ShoppingCart>>

    suspend fun deleteShoppingCartItem(item: ShoppingCart)
}
