package com.project.recipe.addrecipe.model

import com.project.model.recipe.IngredientUnit

data class IngredientUiModel(
    val id: Int,
    val name: String = "",
    val amount: String = "",
    val unit: IngredientUnit = IngredientUnit.COUNT
)
