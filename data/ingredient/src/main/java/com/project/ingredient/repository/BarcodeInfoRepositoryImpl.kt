package com.project.ingredient.repository

import com.project.ingredient.asExternalModel
import com.project.model.barcode.Product
import com.project.network.retrofit.BarcodeApiService
import javax.inject.Inject

class BarcodeInfoRepositoryImpl @Inject constructor(
    private val barcodeApi: BarcodeApiService
): BarcodeInfoRepository {
    override suspend fun getBarcodeInfo(barcode: String): List<Product> {
        val response = barcodeApi.getProductInfo(barcode = barcode)
        val barcodeResponse = response.body()

        return barcodeResponse?.asExternalModel() ?: emptyList()
    }
}
