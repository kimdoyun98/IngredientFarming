package com.project.ingredient.usecase.recipe

import com.project.ingredient.repository.IngredientRepository
import com.project.ingredient.repository.RecipeRepository
import com.project.model.recipe.Recipe
import javax.inject.Inject

class SaveRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository,
) {
    suspend operator fun invoke(recipe: Recipe): Boolean {
        val normalizeIngredients = recipe.ingredients.map { it.copy(name = it.name.normalize()) }

        val newIngredients = normalizeIngredients.map {
            val id = ingredientRepository.getIngredientByName(it.name)?.id
                ?: ingredientRepository.insertUnknownIngredient(it.name)
            it.copy(ingredientId = id)
        }

        return recipeRepository.saveRecipe(recipe.copy(ingredients = newIngredients)).isSuccess
    }

    private fun String.normalize() =
        this
            .trim()
            .lowercase()
            .replace(" ", "")
}
