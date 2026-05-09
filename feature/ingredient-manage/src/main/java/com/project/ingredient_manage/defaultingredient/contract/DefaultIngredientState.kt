package com.project.ingredient_manage.defaultingredient.contract

import com.project.model.ingredient.IngredientCategory

data class DefaultIngredientState(
    val query: String = "",
    val selectedCategory: IngredientCategory? = null,
)
