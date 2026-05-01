package com.project.ingredient.usecase.recipe

import com.project.ingredient.repository.HoldIngredientRepository
import com.project.model.ingredient.HoldIngredientCount
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllHoldIngredientCountUseCase @Inject constructor(
    private val holdIngredientRepository: HoldIngredientRepository,
) {
    operator fun invoke(): Flow<List<HoldIngredientCount>> {
        return holdIngredientRepository.getAllHoldIngredientCount()
    }
}
