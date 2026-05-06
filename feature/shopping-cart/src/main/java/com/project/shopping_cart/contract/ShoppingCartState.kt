package com.project.shopping_cart.contract

import com.project.model.cart.ShoppingCartUiModel
import com.project.model.ingredient.getIndexToIngredientCategory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ShoppingCartState(
    val addState: Boolean = false,
    val cartList: ImmutableList<ShoppingCartUiModel> = persistentListOf(),
    val addItemNameQuery: String = "",
    val addItemCount: String = "0",
    val addItemCategorySelected: Int = -1,
    val addItemIngredientId: Int = -1,
    val saveSuccessItemState: Boolean = false
)

internal fun ShoppingCartState.asShoppingCartUiModel() =
    ShoppingCartUiModel(
        ingredientId = addItemIngredientId,
        name = addItemNameQuery,
        count = addItemCount.toDouble(),
        category = getIndexToIngredientCategory(addItemCategorySelected),
    )
