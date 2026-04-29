package com.project.ingredient.usecase.recipe

import com.project.ingredient.repository.HoldIngredientRepository
import com.project.model.recipe.RecipeIngredient
import javax.inject.Inject

class CheckRecipeIngredientsAvailabilityUseCase @Inject constructor(
    private val holdIngredientRepository: HoldIngredientRepository,
) {
    suspend operator fun invoke(ingredients: List<RecipeIngredient>): HashMap<Int, Boolean> {
        val hashMap = HashMap<Int, Boolean>()

        ingredients.map { ingredient ->
            val holdIngredientCount =
                holdIngredientRepository.getHoldIngredientCountByIngredientId(ingredient.ingredientId)

            hashMap[ingredient.ingredientId] =
                if (holdIngredientCount == 0.0) false
                else if (!ingredient.isAutoDecrement) true
                else if (ingredient.count <= holdIngredientCount) true
                else false
        }

        return hashMap
    }
}
