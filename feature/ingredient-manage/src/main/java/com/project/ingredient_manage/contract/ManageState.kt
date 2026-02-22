package com.project.ingredient_manage.contract

import com.project.model.ingredient.Ingredient
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class ManageState(
    val query: String = "",
    val selectedCategoryIndex: Int = 0,
    val ingredientItems: ImmutableList<Ingredient> = persistentListOf(),
)
