package com.project.model.recipe

data class RecipeListItem(
    val id: Int,
    val image: String?,
    val name: String,
    val category: RecipeCategory,
    val minute: Int,
    val people: Int,
    val ingredients: List<RecipeListItemIngredient>,
)

data class RecipeListItemIngredient(
    val ingredientId: Int,
    val recipeIngredientCount: Double,
    val isAutoDecrement: Boolean,
)
