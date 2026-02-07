package com.project.ingredient.barcode.contract

sealed interface BarcodeEffect {
    data class ToastMessage(val message: String) : BarcodeEffect
    object NavigateSaveIngredientScreen : BarcodeEffect
}
