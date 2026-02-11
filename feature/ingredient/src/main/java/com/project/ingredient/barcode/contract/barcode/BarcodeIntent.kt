package com.project.ingredient.barcode.contract.barcode

sealed interface BarcodeIntent {
    data class BarcodeScan(val barcode: String) : BarcodeIntent
    object NavigateSaveScreen : BarcodeIntent
}
