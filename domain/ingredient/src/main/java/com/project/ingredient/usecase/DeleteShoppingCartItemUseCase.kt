package com.project.ingredient.usecase

import com.project.model.cart.ShoppingCart
import com.project.ingredient.repository.ShoppingCartRepository
import javax.inject.Inject

class DeleteShoppingCartItemUseCase @Inject constructor(
    private val shoppingCartRepository: ShoppingCartRepository,
) {
    suspend operator fun invoke(item: ShoppingCart){
        shoppingCartRepository.deleteShoppingCartItem(item)
    }
}
