package com.project.ingredient.usecase.manage

import com.project.ingredient.repository.HoldIngredientRepository
import com.project.model.ingredient.Ingredient
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