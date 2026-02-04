package com.project.ingredient.usecase

import com.project.ingredient.repository.BarcodeInfoRepository
import com.project.model.barcode.Product
import javax.inject.Inject

class GetBarcodeInfoUseCase @Inject constructor(
    private val barcodeInfoRepository: BarcodeInfoRepository
) {
    suspend operator fun invoke(barcode: String): List<Product> {
        return barcodeInfoRepository.getBarcodeInfo(barcode)
    }
}
