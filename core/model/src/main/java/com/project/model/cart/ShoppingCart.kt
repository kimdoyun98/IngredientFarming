package com.project.model.cart

import com.project.model.ingredient.IngredientCategory

data class ShoppingCart(
    val name: String,
    val count: Int,
    val category: IngredientCategory,
    val success: Boolean,
)
