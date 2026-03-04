package com.project.ingredient.usecase

import com.project.ingredient.repository.HoldIngredientRepository
import com.project.model.ingredient.Ingredient
import javax.inject.Inject

class GetHoldIngredientUseCase @Inject constructor(
    private val holdIngredientRepository: HoldIngredientRepository,
) {
    suspend operator fun invoke(id: Int): Ingredient {
        return holdIngredientRepository.getHoldIngredientById(id)
    }
}
