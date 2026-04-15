package com.project.ingredient.usecase.shopping

import com.project.ingredient.repository.ShoppingCartRepository
import com.project.model.cart.ShoppingCart
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllShoppingCartItemsUseCase @Inject constructor(
    private val shoppingCartRepository: ShoppingCartRepository,
) {
    operator fun invoke(): Flow<List<ShoppingCart>> {
        return shoppingCartRepository.getAllShoppingCartItems()
    }
}