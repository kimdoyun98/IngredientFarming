package com.project.ingredient.barcode.contract.barcode

import com.project.model.barcode.Product

sealed interface BarcodeIntent {
    data class BarcodeScan(val barcode: String) : BarcodeIntent
    data class SelectIngredient(val product: Product) : BarcodeIntent
    object DirectInputClick : BarcodeIntent
    object SnackBarDismissed : BarcodeIntent
}
