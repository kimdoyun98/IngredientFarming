package com.project.ingredient.usecase

import com.project.ingredient.repository.HoldIngredientRepository
import com.project.ingredient.repository.IngredientRepository
import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetAllHoldIngredientUseCase @Inject constructor(
    private val holdIngredientRepository: HoldIngredientRepository,
) {
    operator fun invoke(): Flow<List<Ingredient>> {
        return holdIngredientRepository.getAllHoldIngredient()
            .onEach {
                it.sortedBy { ingredient -> ingredient.expirationDate }
            }
    }
}
