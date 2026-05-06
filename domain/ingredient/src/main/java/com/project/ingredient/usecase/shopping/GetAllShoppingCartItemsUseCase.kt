package com.project.ingredient.usecase.shopping

import com.project.ingredient.repository.IngredientRepository
import com.project.ingredient.repository.ShoppingCartRepository
import com.project.model.cart.ShoppingCartUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllShoppingCartItemsUseCase @Inject constructor(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val ingredientRepository: IngredientRepository,
) {
    operator fun invoke(): Flow<List<ShoppingCartUiModel>> {
        return shoppingCartRepository.getAllShoppingCartItems()
            .map { shoppingCarts ->
                shoppingCarts.map { shoppingCart ->
                    val info = ingredientRepository.getIngredientById(shoppingCart.ingredientId)!!

                    ShoppingCartUiModel(
                        id = shoppingCart.id,
                        ingredientId = info.id,
                        name = info.name,
                        count = shoppingCart.count,
                        category = info.category,
                    )
                }
            }
    }
}
