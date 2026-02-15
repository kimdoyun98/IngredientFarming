package com.project.ingredient.barcode.contract.barcode

import androidx.compose.runtime.Immutable
import com.project.ingredient.barcode.ui.barcode.util.BarcodeScanStatus
import com.project.model.barcode.Product

@Immutable
data class BarcodeState(
    val scanStatus: BarcodeScanStatus = BarcodeScanStatus.Idle,
    val selectProduct: Product = Product(barcode = "", name = ""),
    val onScan: (String) -> Unit = {},
)
