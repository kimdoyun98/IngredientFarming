package com.project.shopping_cart.contract

import com.project.model.cart.ShoppingCart
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ShoppingCartState(
    val addState: Boolean = false,
    val cartList: ImmutableList<ShoppingCart> = persistentListOf(),
    val addItemNameQuery: String = "",
    val addItemCount: String = "0",
    val addItemCategorySelected: Int = -1,
    val saveSuccessItemState: Boolean = false
)
