package com.project.model.ingredient

import androidx.compose.runtime.Immutable

@Immutable
data class IngredientInfo(
    val id: Int,
    val name: String,
    val category: IngredientCategory,
    val store: IngredientStore
)
