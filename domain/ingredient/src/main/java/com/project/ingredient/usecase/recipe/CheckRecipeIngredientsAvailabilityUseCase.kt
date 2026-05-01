package com.project.ingredient.usecase.recipe

import com.project.model.ingredient.HoldIngredientCount
import com.project.model.recipe.CheckRecipeAvailability
import javax.inject.Inject

class CheckRecipeIngredientsAvailabilityUseCase @Inject constructor() {
    operator fun invoke(
        holdIngredientCount: Map<Int, HoldIngredientCount>,
        recipeIngredients: List<CheckRecipeAvailability>,
    ): HashMap<Int, Boolean> {
        val hashMap = HashMap<Int, Boolean>()

        recipeIngredients.map { ingredient ->
            val holdCount = holdIngredientCount[ingredient.ingredientId]?.count ?: 0.0

            hashMap[ingredient.ingredientId] =
                if (holdCount == 0.0) false
                else if (!ingredient.isAutoDecrement) true
                else if (ingredient.count <= holdCount) true
                else false
        }

        return hashMap
    }
}
