package com.project.model.barcode

import androidx.compose.runtime.Immutable

@Immutable
data class Product(
    val barcode: String,
    val name: String,
)
