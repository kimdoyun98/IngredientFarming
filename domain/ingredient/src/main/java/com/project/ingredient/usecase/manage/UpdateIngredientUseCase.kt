package com.project.ingredient.usecase.manage

import com.project.ingredient.repository.IngredientRepository
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore
import javax.inject.Inject

class UpdateIngredientUseCase @Inject constructor(
    private val ingredientRepository: IngredientRepository,
) {
    suspend operator fun invoke(
        id: Int,
        category: IngredientCategory,
        store: IngredientStore
    ): Result<Unit> {
        return ingredientRepository.updateUnknownIngredient(
            id = id,
            category = category,
            store = store
        )
    }
}
