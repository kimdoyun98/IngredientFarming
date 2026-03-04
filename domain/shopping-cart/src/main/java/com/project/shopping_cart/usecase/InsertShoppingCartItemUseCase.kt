package com.project.shopping_cart.usecase

import com.project.model.cart.ShoppingCart
import com.project.shopping_cart.repository.ShoppingCartRepository
import javax.inject.Inject

class InsertShoppingCartItemUseCase @Inject constructor(
    private val shoppingCartRepository: ShoppingCartRepository
) {

    suspend operator fun invoke(item: ShoppingCart) {
        shoppingCartRepository.insertShoppingCartItem(item)
    }
}
