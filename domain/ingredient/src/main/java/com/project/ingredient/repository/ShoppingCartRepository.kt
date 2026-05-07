package com.project.ingredient.repository

import com.project.model.cart.ShoppingCart
import kotlinx.coroutines.flow.Flow

interface ShoppingCartRepository {
    suspend fun insertShoppingCartItem(ingredientId: Int, count: Double)
    suspend fun updateShoppingCartItem(cart: ShoppingCart, count: Double)
    fun getAllShoppingCartItems(): Flow<List<ShoppingCart>>
    suspend fun deleteShoppingCartItem(item: ShoppingCart)
    suspend fun getShoppingCartItemByIngredientId(id: Int): ShoppingCart?
    fun getShoppingCartItemsCount(): Flow<Int>
}
