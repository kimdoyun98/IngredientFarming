package com.project.ingredient.usecase.recipe

import com.project.ingredient.repository.RecipeRepository
import com.project.model.recipe.Recipe
import javax.inject.Inject

class GetRecipeInfoUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository,
) {
    suspend operator fun invoke(recipeId: Int): Recipe {
        return recipeRepository.getRecipeInfoById(recipeId)
    }
}
