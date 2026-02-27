package com.project.model.ingredient

data class IngredientInfo(
    val id: Int,
    val name: String,
    val category: IngredientCategory,
    val store: IngredientStore
)
