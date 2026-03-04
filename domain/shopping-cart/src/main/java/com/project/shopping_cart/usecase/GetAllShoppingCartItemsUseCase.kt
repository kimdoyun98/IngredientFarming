package com.project.shopping_cart.usecase

import com.project.model.cart.ShoppingCart
import com.project.shopping_cart.repository.ShoppingCartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllShoppingCartItemsUseCase @Inject constructor(
    private val shoppingCartRepository: ShoppingCartRepository,
) {
    operator fun invoke(): Flow<List<ShoppingCart>> {
        return shoppingCartRepository.getAllShoppingCartItems()
    }
}
