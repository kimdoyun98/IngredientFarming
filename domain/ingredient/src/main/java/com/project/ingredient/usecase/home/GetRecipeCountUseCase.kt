package com.project.ingredient.usecase.home

import com.project.ingredient.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecipeCountUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(): Flow<Int> {
        return recipeRepository.getRecipeCount()
    }
}
