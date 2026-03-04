package com.project.ingredient.usecase

import com.project.ingredient.repository.IngredientRepository
import com.project.model.cart.ShoppingCart
import com.project.model.ingredient.Ingredient
import javax.inject.Inject

class SaveCartSuccessItemsUseCase @Inject constructor(
    private val ingredientRepository: IngredientRepository,
) {
    suspend operator fun invoke(items: List<ShoppingCart>) {
        ingredientRepository.insertIngredient(
            items.map {
                Ingredient(
                    name = it.name,
                    count = it.count,
                    category = it.category
                )
            }
        )
    }
}
