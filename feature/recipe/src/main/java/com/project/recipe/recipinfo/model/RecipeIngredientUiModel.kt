package com.project.recipe.recipinfo.model

import androidx.compose.runtime.Stable
import com.project.model.recipe.IngredientUnit
import com.project.model.recipe.RecipeIngredient

@Stable
data class RecipeIngredientUiModel(
    val id: Int,
    val ingredientId: Int,
    val name: String,
    val count: Double,
    val unit: IngredientUnit,
    val isAvailable: Boolean
)

internal fun RecipeIngredient.asUiModel(isAvailable: Boolean = false) =
    RecipeIngredientUiModel(
        id = id,
        ingredientId = ingredientId,
        name = name,
        count = count,
        unit = unit,
        isAvailable = isAvailable
    )
