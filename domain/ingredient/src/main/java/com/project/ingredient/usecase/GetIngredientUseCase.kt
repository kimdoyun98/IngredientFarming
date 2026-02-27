package com.project.ingredient.usecase

import com.project.ingredient.repository.IngredientRepository
import com.project.model.ingredient.IngredientInfo
import javax.inject.Inject

class GetIngredientUseCase @Inject constructor(
    private val ingredientRepository: IngredientRepository
) {
    suspend operator fun invoke(name: String): IngredientInfo?{
        return ingredientRepository.getIngredientByName(name)
    }
}
