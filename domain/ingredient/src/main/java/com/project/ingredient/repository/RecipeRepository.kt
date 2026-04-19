package com.project.ingredient.repository

import com.project.model.recipe.Recipe
import com.project.model.recipe.RecipeListItem
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun saveRecipe(recipe: Recipe): Result<Unit>

    fun getAllRecipe(): Flow<List<RecipeListItem>>
}
