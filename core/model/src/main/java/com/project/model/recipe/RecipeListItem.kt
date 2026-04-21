package com.project.model.recipe

data class RecipeListItem(
    val id: Int,
    val photo: String,
    val name: String,
    val category: RecipeCategory,
    val minute: Int,
    val people: Int,
    val ingredients: List<Int>,
)
