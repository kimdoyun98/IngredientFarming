package com.project.ingredient.barcode.ui.barcode.util

import com.project.model.barcode.Product
import kotlinx.collections.immutable.ImmutableList

sealed interface BarcodeScanStatus {
    object Idle : BarcodeScanStatus
    object Scanning : BarcodeScanStatus
    data class Success(val products: ImmutableList<Product>) : BarcodeScanStatus
    data class Error(val message: String) : BarcodeScanStatus
}
