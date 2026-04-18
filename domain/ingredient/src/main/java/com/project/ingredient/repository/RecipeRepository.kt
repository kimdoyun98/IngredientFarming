package com.project.ingredient.repository

import com.project.model.recipe.Recipe

interface RecipeRepository {
    suspend fun saveRecipe(recipe: Recipe): Result<Unit>
}
