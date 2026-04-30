package com.project.model.recipe

data class RecipeFilter(
    val query: String = "",
    val category: RecipeCategory? = null
)
