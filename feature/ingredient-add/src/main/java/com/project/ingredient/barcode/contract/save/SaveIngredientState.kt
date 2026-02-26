package com.project.ingredient.barcode.contract.save

import androidx.compose.runtime.Stable
import com.project.ingredient.barcode.contract.directInput.DirectInputState
import com.project.model.ingredient.Ingredient
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class SaveIngredientState(
    val ingredientList: ImmutableList<Ingredient> = persistentListOf(),
    val updateIngredient: DirectInputState = DirectInputState(),
    val openUpdateBottomSheetState: Boolean = false,
    val openAddBottomSheetState: Boolean = false,
)
