package com.project.ingredient.usecase.recipe

import com.project.ingredient.repository.RecipeRepository
import com.project.model.recipe.Recipe
import javax.inject.Inject

class SaveRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(recipe: Recipe): Boolean {

        return recipeRepository.saveRecipe(recipe).isSuccess
    }
}
