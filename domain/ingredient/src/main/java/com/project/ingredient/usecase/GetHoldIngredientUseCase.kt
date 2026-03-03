package com.project.ingredient.usecase

import com.project.ingredient.repository.IngredientRepository
import com.project.model.ingredient.Ingredient
import javax.inject.Inject

class GetHoldIngredientUseCase @Inject constructor(
    private val ingredientRepository: IngredientRepository,
) {
    suspend operator fun invoke(id: Int): Ingredient {
        return ingredientRepository.getHoldIngredientById(id)
    }
}
