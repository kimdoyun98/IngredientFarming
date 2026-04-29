package com.project.model.cart

import com.project.model.ingredient.IngredientCategory

data class ShoppingCart(
    val id: Int = 0,
    val name: String,
    val count: Double,
    val category: IngredientCategory,
    val success: Boolean,
)
