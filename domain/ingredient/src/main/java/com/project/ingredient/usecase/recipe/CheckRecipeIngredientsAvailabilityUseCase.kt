package com.project.ingredient.usecase.recipe

import com.project.ingredient.repository.HoldIngredientRepository
import com.project.model.recipe.IngredientAvailability
import com.project.model.recipe.RecipeIngredient
import javax.inject.Inject

class CheckRecipeIngredientsAvailabilityUseCase @Inject constructor(
    private val holdIngredientRepository: HoldIngredientRepository,
) {
    suspend operator fun invoke(ingredients: List<RecipeIngredient>): List<IngredientAvailability> {

        return ingredients.map { ingredient ->
            val recipeCount = ingredient.count
            val holdIngredientCount =
                holdIngredientRepository.getHoldIngredientCountByIngredientId(ingredient.ingredientId)

            IngredientAvailability(
                ingredientId = ingredient.ingredientId,
                isAvailability =
                    if (holdIngredientCount == 0.0) false
                    else if (!ingredient.isAutoDecrement) true
                    else if (recipeCount <= holdIngredientCount) true
                    else false
            )
        }
    }
}
