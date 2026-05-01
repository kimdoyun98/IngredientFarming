package com.project.model.recipe

data class CheckRecipeAvailability(
    val recipeId: Int,
    val ingredientId: Int,
    val count: Double,
    val isAutoDecrement: Boolean,
)

fun RecipeListItemIngredient.asCheckRecipeAvailability(recipeId: Int) =
    CheckRecipeAvailability(
        recipeId = recipeId,
        ingredientId = ingredientId,
        count = recipeIngredientCount,
        isAutoDecrement = isAutoDecrement
    )

fun RecipeIngredient.asCheckRecipeAvailability(recipeId: Int) =
    CheckRecipeAvailability(
        recipeId = recipeId,
        ingredientId = ingredientId,
        count = count,
        isAutoDecrement = isAutoDecrement
    )
