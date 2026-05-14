package com.project.ingredient_manage.defaultingredient.contract

sealed interface DefaultIngredientEffect {
    object NavigateToBack : DefaultIngredientEffect
    data class UpdateDialogError(val message: String?) : DefaultIngredientEffect
}
