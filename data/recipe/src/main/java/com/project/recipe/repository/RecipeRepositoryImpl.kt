package com.project.recipe.repository

import androidx.room.Transaction
import com.project.database.dao.RecipeDao
import com.project.ingredient.repository.RecipeRepository
import com.project.model.recipe.Recipe
import com.project.model.recipe.RecipeListItem
import com.project.recipe.asExternalModel
import com.project.recipe.asRecipeEntity
import com.project.recipe.asRecipeIngredientEntity
import com.project.recipe.asRecipeStepEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val recipeDao: RecipeDao
) : RecipeRepository {

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

    override fun getAllRecipe(): Flow<List<RecipeListItem>> {
        return recipeDao.getAllRecipe()
            .map {
                it.map { recipeWithIngredient ->
                    recipeWithIngredient.asExternalModel()
                }
            }

    }

    override suspend fun getRecipeInfoById(recipeId: Int): Recipe {
        return recipeDao.getRecipeDetail(recipeId).asExternalModel()
    }
}
