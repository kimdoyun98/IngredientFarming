package com.project.ingredient.usecase.home

import com.project.ingredient.repository.IngredientRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpirationDateSoonCountUseCase @Inject constructor(
    private val ingredientRepository: IngredientRepository
) {
    operator fun invoke(): Flow<Int> {
        return ingredientRepository.getCountExpiringInThreeDays()
    }
}
