package com.project.model.recipe

data class Recipe(
    val id: Int,
    val name: String,
    val imagePath: String?,
    val category: RecipeCategory,
    val minute: Int,
    val people: Int,
    val ingredients: List<RecipeIngredient>,
    val recipeSteps: List<RecipeStep>
)

data class RecipeIngredient(
    val id: Int,
    val ingredientId: Int,
    val name: String,
    val count: Double,
    val unit: IngredientUnit,
    val isAutoDecrement: Boolean,
)

data class RecipeStep(
    val id: Int,
    val number: Int,
    val description: String,
)
