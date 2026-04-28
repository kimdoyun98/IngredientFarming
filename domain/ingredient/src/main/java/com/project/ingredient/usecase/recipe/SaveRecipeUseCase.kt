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

        //TODO 반복 호출 개선 방안 고려
        val newIngredients = recipe.ingredients.map {
            val id = ingredientRepository.getIngredientByName(it.name)?.id
                ?: ingredientRepository.insertUnknownIngredient(it.name)
            it.copy(ingredientId = id)
        }

        return recipeRepository.saveRecipe(recipe.copy(ingredients = newIngredients)).isSuccess
    }
}
