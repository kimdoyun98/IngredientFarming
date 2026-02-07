package com.project.ingredient.barcode.contract

import androidx.compose.runtime.Immutable
import com.project.model.barcode.Product

@Immutable
data class BarcodeState (
    val scanStatus: BarcodeScanStatus = BarcodeScanStatus.Idle,
    val onScan: (String) -> Unit = {}
)

sealed interface BarcodeScanStatus {
    object Idle : BarcodeScanStatus
    object Scanning : BarcodeScanStatus
    data class Success(val product: List<Product>) : BarcodeScanStatus
    data class Error(val message: String) : BarcodeScanStatus
}
