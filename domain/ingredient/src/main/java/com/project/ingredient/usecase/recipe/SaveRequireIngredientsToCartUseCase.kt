package com.project.ingredient.usecase.recipe

import com.project.ingredient.repository.HoldIngredientRepository
import com.project.ingredient.repository.ShoppingCartRepository
import com.project.ingredient.usecase.GetIngredientUseCase
import com.project.model.cart.ShoppingCart
import com.project.model.ingredient.IngredientCategory
import com.project.model.recipe.RecipeIngredient
import javax.inject.Inject

class SaveRequireIngredientsToCartUseCase @Inject constructor(
    private val getIngredientUseCase: GetIngredientUseCase,
    private val holdIngredientRepository: HoldIngredientRepository,
    private val shoppingCartRepository: ShoppingCartRepository,
) {
    suspend operator fun invoke(requireIngredients: List<RecipeIngredient>) {
        //현재는 보유 중인 장보기 식재료 + 레시피 부족 식재료

        requireIngredients.forEach { ingredient ->
            val category =
                getIngredientUseCase.invoke(ingredient.name)?.category ?: IngredientCategory.OTHER
            val holdIngredientCount =
                holdIngredientRepository.getHoldIngredientCountByIngredientId(ingredient.ingredientId)
            val isInShoppingCart = shoppingCartRepository.getShoppingCartItemByName(ingredient.name) != null

            if(!(isInShoppingCart && !ingredient.isAutoDecrement)){
                shoppingCartRepository.insertShoppingCartItem(
                    ShoppingCart(
                        name = ingredient.name,
                        count = if (ingredient.isAutoDecrement) ingredient.count - holdIngredientCount else 1.0,
                        category = category,
                        success = false
                    )
                )
            }
        }
    }
}
