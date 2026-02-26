package com.project.ingredient.barcode.contract.barcode

sealed interface BarcodeEffect {
    object NavigateSaveIngredientScreen : BarcodeEffect
    object NavigateDirectInputScreen : BarcodeEffect
}
