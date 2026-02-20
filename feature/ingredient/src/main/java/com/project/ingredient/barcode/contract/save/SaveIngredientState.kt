package com.project.ingredient.barcode.contract.save

import androidx.compose.runtime.Stable
import com.project.ingredient.barcode.contract.directInput.DirectInputState
import com.project.model.ingredient.Ingredient

@Stable
data class SaveIngredientState(
    val ingredientList: List<Ingredient> = emptyList(),
    val updateIngredient: DirectInputState = DirectInputState(),
    val openUpdateBottomSheetState: Boolean = false,
    val openAddBottomSheetState: Boolean = false,
)
