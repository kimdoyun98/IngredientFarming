package com.project.ingredient_manage.defaultingredient.util

sealed interface UpdateDefaultIngredientState {
    object Idle : UpdateDefaultIngredientState
    object Loading : UpdateDefaultIngredientState
    object Success : UpdateDefaultIngredientState
}
