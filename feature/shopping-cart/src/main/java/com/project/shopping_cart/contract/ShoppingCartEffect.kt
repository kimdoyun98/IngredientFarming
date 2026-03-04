package com.project.shopping_cart.contract

sealed interface ShoppingCartEffect {
    object PopBackStack: ShoppingCartEffect
}
