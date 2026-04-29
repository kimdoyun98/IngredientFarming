package com.project.recipe.addrecipe.model

import androidx.compose.runtime.Stable
import com.project.model.recipe.IngredientUnit

@Stable
data class IngredientUiModel(
    val id: Int,
    val name: String = "",
    val amount: String = "",
    val unit: IngredientUnit = IngredientUnit.COUNT
)
