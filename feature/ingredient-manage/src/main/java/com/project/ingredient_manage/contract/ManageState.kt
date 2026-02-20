package com.project.ingredient_manage.contract

import com.project.model.ingredient.Ingredient

data class ManageState(
    val query: String = "",
    val selectedCategoryIndex: Int = 0,
    val ingredientItems: List<Ingredient> = emptyList(),
)
