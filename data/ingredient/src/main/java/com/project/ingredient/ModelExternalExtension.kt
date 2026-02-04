package com.project.ingredient

import com.project.model.barcode.Product
import com.project.network.retrofit.model.BarcodeResponse

fun BarcodeResponse.asExternalModel(): List<Product> {
    return this.i2570?.row?.map {
        Product(
            barcode = it.brcd_no,
            name = it.prdt_nm
        )
    } ?: emptyList()
}
