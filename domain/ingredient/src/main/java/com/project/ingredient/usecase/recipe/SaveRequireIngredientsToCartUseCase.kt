package com.project.ingredient.usecase.recipe

import com.project.ingredient.repository.HoldIngredientRepository
import com.project.ingredient.repository.ShoppingCartRepository
import com.project.model.recipe.RecipeIngredient
import javax.inject.Inject

class SaveRequireIngredientsToCartUseCase @Inject constructor(
    private val holdIngredientRepository: HoldIngredientRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) {
    suspend operator fun invoke(requireIngredients: List<RecipeIngredient>) {
        requireIngredients.forEach { ingredient ->
            val cart =
                shoppingCartRepository.getShoppingCartItemByIngredientId(ingredient.ingredientId)

            val holdIngredientCount =
                holdIngredientRepository.getHoldIngredientCountByIngredientId(ingredient.ingredientId)

            val isInShoppingCart =
                shoppingCartRepository.getShoppingCartItemByIngredientId(ingredient.ingredientId) != null

            if (!isInShoppingCart || ingredient.isAutoDecrement) {
                if (cart == null) {
                    shoppingCartRepository.insertShoppingCartItem(
                        ingredientId = ingredient.ingredientId,
                        count = getShoppingCartItemCount(ingredient, holdIngredientCount),
                    )
                } else {
                    shoppingCartRepository.updateShoppingCartItem(
                        cart = cart,
                        count = getShoppingCartItemCount(ingredient, holdIngredientCount)
                    )
                }
            }
        }
    }

    private fun getShoppingCartItemCount(
        ingredient: RecipeIngredient,
        holdIngredientCount: Double
    ): Double {
        return if (ingredient.isAutoDecrement) ingredient.count - holdIngredientCount else 1.0
    }
}
