package com.project.ingredient.repository

import com.project.model.cart.ShoppingCart
import kotlinx.coroutines.flow.Flow

interface ShoppingCartRepository {
    suspend fun insertShoppingCartItem(item: ShoppingCart)
    fun getAllShoppingCartItems(): Flow<List<ShoppingCart>>
    suspend fun deleteShoppingCartItem(item: ShoppingCart)
    suspend fun getShoppingCartItemByName(name: String): ShoppingCart?
}
