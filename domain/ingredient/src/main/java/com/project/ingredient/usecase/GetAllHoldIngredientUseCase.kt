package com.project.ingredient.usecase

import com.project.ingredient.repository.IngredientRepository
import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetAllHoldIngredientUseCase @Inject constructor(
    private val ingredientRepository: IngredientRepository,
) {
    operator fun invoke(): Flow<List<Ingredient>> {
        return ingredientRepository.getAllHoldIngredient()
            .onEach {
                it.sortedBy { ingredient -> ingredient.expirationDate }
            }
    }
}
