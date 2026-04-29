package com.project.ingredient.usecase.manage

import com.project.ingredient.repository.HoldIngredientRepository
import javax.inject.Inject

class UpdateHoldIngredientCountUseCase @Inject constructor(
    private val holdIngredientRepository: HoldIngredientRepository,
) {
    suspend operator fun invoke(id: Int, count: Double) {
        holdIngredientRepository.updateHoldIngredientCount(id, count)
    }
}