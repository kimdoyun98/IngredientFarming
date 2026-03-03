package com.project.ingredient.usecase

import com.project.ingredient.repository.IngredientRepository
import javax.inject.Inject

class UpdateHoldIngredientCountUseCase @Inject constructor(
    private val ingredientRepository: IngredientRepository
) {
    suspend operator fun invoke(id: Int, count: Int){
        ingredientRepository.updateHoldIngredientCount(id, count)
    }
}
