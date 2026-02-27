package com.project.ingredient.usecase

import com.project.ingredient.repository.IngredientRepository
import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientCategory
import javax.inject.Inject

class SearchIngredientUseCase @Inject constructor(
    private val ingredientRepository: IngredientRepository,
) {
    suspend operator fun invoke(query: String, category: IngredientCategory?): List<Ingredient> {
        return ingredientRepository.searchIngredients(query, category)
            .sortedBy { it.expirationDate }
    }
}
