package com.project.ingredient.usecase.home

import com.project.ingredient.repository.ShoppingCartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShoppingCartItemsCountUseCase @Inject constructor(
    private val shoppingCartRepository: ShoppingCartRepository
) {

    operator fun invoke(): Flow<Int> {
        return shoppingCartRepository.getShoppingCartItemsCount()
    }
}
