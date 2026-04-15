package com.project.ingredient.usecase.shopping

import com.project.ingredient.repository.ShoppingCartRepository
import com.project.model.cart.ShoppingCart
import javax.inject.Inject

class InsertShoppingCartItemUseCase @Inject constructor(
    private val shoppingCartRepository: ShoppingCartRepository
) {

    suspend operator fun invoke(item: ShoppingCart) {
        shoppingCartRepository.insertShoppingCartItem(item)
    }
}