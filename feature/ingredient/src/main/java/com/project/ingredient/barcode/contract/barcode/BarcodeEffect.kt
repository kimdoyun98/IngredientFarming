package com.project.ingredient.barcode.contract.barcode

sealed interface BarcodeEffect {
    data class ToastMessage(val message: String) : BarcodeEffect
    object NavigateSaveIngredientScreen : BarcodeEffect
}
