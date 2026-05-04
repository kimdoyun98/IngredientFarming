package com.project.model.cart

data class ShoppingCart(
    val id: Int = 0,
    val ingredientId: Int,
    val count: Double,
)
