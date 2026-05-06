package com.project.ingredient.usecase.shopping

import com.project.ingredient.repository.IngredientRepository
import com.project.ingredient.repository.ShoppingCartRepository
import com.project.model.cart.ShoppingCartUiModel
import javax.inject.Inject

class InsertShoppingCartItemUseCase @Inject constructor(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val ingredientRepository: IngredientRepository,
) {

    suspend operator fun invoke(item: ShoppingCartUiModel) {
        val ingredientId = if (item.ingredientId == -1) {
            ingredientRepository.insertUnknownIngredient(item.name, item.category)
        } else {
            item.ingredientId
        }
        val cart = shoppingCartRepository.getShoppingCartItemByIngredientId(ingredientId)

        if (cart == null) {
            shoppingCartRepository.insertShoppingCartItem(
                ingredientId = ingredientId,
                count = item.count,
            )
        } else {
            shoppingCartRepository.updateShoppingCartItem(
                cart = cart,
                count = item.count
            )
        }
    }
}
