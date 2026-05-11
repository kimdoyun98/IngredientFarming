package com.project.ingredient_manage.defaultingredient.contract

import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore

data class DefaultIngredientState(
    val query: String = "",
    val selectedCategory: IngredientCategory? = null,
    val showDialog: Boolean = false,
    val dialogIngredientName: String = "",
    val selectedDialogCategory: IngredientCategory? = null,
    val selectedDialogStore: IngredientStore? = null,
)
