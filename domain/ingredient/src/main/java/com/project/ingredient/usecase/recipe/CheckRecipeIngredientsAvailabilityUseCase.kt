package com.project.ingredient.usecase.recipe

import com.project.ingredient.repository.HoldIngredientRepository
import com.project.model.recipe.IngredientAvailability
import com.project.model.recipe.RecipeIngredient
import com.project.model.recipe.asIngredientAvailability
import javax.inject.Inject

class CheckRecipeIngredientsAvailabilityUseCase @Inject constructor(
    private val holdIngredientRepository: HoldIngredientRepository,
) {
    suspend operator fun invoke(ingredients: List<RecipeIngredient>): List<IngredientAvailability> {

        return ingredients.map { ingredient ->

            ingredient.asIngredientAvailability(
                recipeCount = ingredient.count,
                holdIngredientCount =
                    holdIngredientRepository.getHoldIngredientCountByIngredientId(ingredient.ingredientId)
            )
        }
    }
}
