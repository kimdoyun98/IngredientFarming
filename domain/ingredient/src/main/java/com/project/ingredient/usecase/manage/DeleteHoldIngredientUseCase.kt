package com.project.ingredient.usecase.manage

import com.project.ingredient.repository.HoldIngredientRepository
import javax.inject.Inject

class DeleteHoldIngredientUseCase @Inject constructor(
    private val holdIngredientRepository: HoldIngredientRepository
) {
    suspend operator fun invoke(ids: List<Int>) {
        holdIngredientRepository.deleteHoldIngredientByIds(ids)
    }
}