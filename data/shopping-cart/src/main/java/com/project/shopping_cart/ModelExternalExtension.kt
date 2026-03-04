package com.project.shopping_cart

import com.project.database.model.ShoppingCartEntity
import com.project.model.cart.ShoppingCart

fun ShoppingCart.asShoppingCartEntity() = ShoppingCartEntity(
    id = id,
    name = name,
    count = count,
    category = category
)
