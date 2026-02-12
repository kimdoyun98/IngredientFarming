package com.project.ingredient.barcode.contract.save

import androidx.compose.runtime.Stable
import com.project.model.barcode.Ingredient

@Stable
data class SaveIngredientState(
    val ingredientList: List<Ingredient> = emptyList()
)
