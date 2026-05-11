package com.project.ingredient_manage.defaultingredient.contract

import com.project.model.ingredient.DefaultIngredient

sealed interface DefaultIngredientEffect {
    object NavigateToBack : DefaultIngredientEffect
}
