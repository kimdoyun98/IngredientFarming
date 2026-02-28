package com.project.ingredient.usecase

import com.project.ingredient.repository.IngredientRepository
import javax.inject.Inject

class DeleteHoldIngredientUseCase @Inject constructor(
    private val ingredientRepository: IngredientRepository
){
    suspend operator fun invoke(ids: List<Int>){
        ingredientRepository.deleteHoldIngredientByIds(ids)
    }
}
