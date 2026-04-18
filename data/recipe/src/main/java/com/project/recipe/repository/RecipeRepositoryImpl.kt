package com.project.recipe.repository

import androidx.room.Transaction
import com.project.database.dao.RecipeDao
import com.project.ingredient.repository.RecipeRepository
import com.project.model.recipe.Recipe
import com.project.recipe.asRecipeEntity
import com.project.recipe.asRecipeIngredientEntity
import com.project.recipe.asRecipeStepEntity
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao
): RecipeRepository {

    @Transaction
    override suspend fun saveRecipe(recipe: Recipe): Result<Unit> {
        return runCatching {
            val id = recipeDao.insertRecipe(recipe.asRecipeEntity())

            recipe.recipeSteps.map { step ->
                recipeDao.insertRecipeStep(step.asRecipeStepEntity(id.toInt()))
            }

            recipe.ingredients.map { ingredient ->
                recipeDao.insertRecipeIngredient(ingredient.asRecipeIngredientEntity(id.toInt()))
            }
        }
    }
}
