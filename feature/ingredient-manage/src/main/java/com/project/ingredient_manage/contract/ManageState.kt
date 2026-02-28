package com.project.ingredient_manage.contract

import com.project.model.ingredient.Ingredient
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf

data class ManageState(
    val query: String = "",
    val selectedCategoryIndex: Int = 0,
    val ingredientItems: ImmutableList<Ingredient> = persistentListOf(),
    val deleteOptionsState: Boolean = false,
    val allSelectedState: Boolean = false,
    val selectedItems: ImmutableMap<Int, Boolean> = persistentMapOf()
)
