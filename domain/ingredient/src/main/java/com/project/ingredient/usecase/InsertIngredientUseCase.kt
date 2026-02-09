package com.project.ingredient.usecase

import com.project.ingredient.repository.IngredientRepository
import com.project.model.barcode.Ingredient
import javax.inject.Inject

class InsertIngredientUseCase @Inject constructor(
    private val ingredientRepository: IngredientRepository,
) {
    suspend operator fun invoke(igdList: List<Ingredient>) {
        ingredientRepository.insertIngredient(igdList)
    }
}
