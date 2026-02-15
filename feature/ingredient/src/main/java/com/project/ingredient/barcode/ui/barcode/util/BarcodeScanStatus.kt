package com.project.ingredient.barcode.ui.barcode.util

import com.project.model.barcode.Product

sealed interface BarcodeScanStatus {
    object Idle : BarcodeScanStatus
    object Scanning : BarcodeScanStatus
    data class Success(val products: List<Product>) : BarcodeScanStatus
    data class Error(val message: String) : BarcodeScanStatus
}
