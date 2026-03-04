package com.project.shopping_cart.usecase

import com.project.model.cart.ShoppingCart
import com.project.shopping_cart.repository.ShoppingCartRepository
import javax.inject.Inject

class DeleteShoppingCartItemUseCase @Inject constructor(
    private val shoppingCartRepository: ShoppingCartRepository,
) {
    suspend operator fun invoke(item: ShoppingCart){
        shoppingCartRepository.deleteShoppingCartItem(item)
    }
}
