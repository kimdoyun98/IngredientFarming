package com.project.ingredient.barcode.contract.barcode

import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore

sealed interface BarcodeEffect {
    data class NavigateSaveIngredientScreen(
        val name: String,
        val category: IngredientCategory = IngredientCategory.OTHER,
        val store: IngredientStore = IngredientStore.ROOM_TEMPERATURE
    ) : BarcodeEffect
    object NavigateDirectInputScreen : BarcodeEffect
}
