package com.project.ingredient.repository

import com.project.model.barcode.Product

interface BarcodeInfoRepository {
    suspend fun getBarcodeInfo(barcode: String): List<Product>
}
