package com.project.model.cart

import com.project.model.ingredient.IngredientCategory

data class ShoppingCartUiModel(
    val id: Int = 0,
    val ingredientId: Int = -1,
    val name: String,
    val count: Double,
    val category: IngredientCategory,
    val success: Boolean = false,
)

fun ShoppingCartUiModel.asShoppingCart() =
    ShoppingCart(
        id = id,
        ingredientId = ingredientId,
        count = count
    )
