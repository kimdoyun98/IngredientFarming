package com.project.ingredient_manage.defaultingredient.contract

import com.project.ingredient_manage.defaultingredient.util.UpdateDefaultIngredientState
import com.project.model.ingredient.DefaultIngredient
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore

data class DefaultIngredientState(
    val query: String = "",
    val selectedCategory: IngredientCategory? = null,
    val showDialog: Boolean = false,
    val selectedIngredient: DefaultIngredient? = null,
    val selectedDialogCategory: IngredientCategory? = null,
    val selectedDialogStore: IngredientStore? = null,
    val updateState: UpdateDefaultIngredientState = UpdateDefaultIngredientState.Idle
)
