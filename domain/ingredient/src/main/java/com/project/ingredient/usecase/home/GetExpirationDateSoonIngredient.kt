package com.project.ingredient.usecase.home

import com.project.ingredient.repository.IngredientRepository
import com.project.model.ingredient.ExpirationDateSoonIngredient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpirationDateSoonIngredient @Inject constructor(
    private val ingredientRepository: IngredientRepository
) {
    operator fun invoke(): Flow<List<ExpirationDateSoonIngredient>> {
        return ingredientRepository.getExpirationDateSoonIngredient()
    }
}
