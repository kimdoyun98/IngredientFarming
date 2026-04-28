package com.project.model.recipe

data class IngredientAvailability(
    val ingredientId: Int,
    val isAvailability: Boolean,
)

fun RecipeIngredient.asIngredientAvailability(
    recipeCount: Double,
    holdIngredientCount: Double,
) = IngredientAvailability(
    ingredientId = ingredientId,
    isAvailability =
        if (holdIngredientCount == 0.0) false
        else if (!isAutoDecrement) true
        else if (recipeCount <= holdIngredientCount) true
        else false
)
