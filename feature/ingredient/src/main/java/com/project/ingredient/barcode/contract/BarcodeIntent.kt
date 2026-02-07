package com.project.ingredient.barcode.contract

sealed interface BarcodeIntent {
    data class BarcodeScan(val barcode: String) : BarcodeIntent
}
