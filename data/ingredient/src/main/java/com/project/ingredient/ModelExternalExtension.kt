package com.project.ingredient

import com.project.model.barcode.Ingredient
import com.project.model.barcode.Product
import com.project.network.retrofit.model.BarcodeResponse

fun BarcodeResponse.asExternalModel(): List<Product> {
    return this.i2570.row.map {
        Product(
            barcode = it.brcd_no,
            name = it.prdt_nm
        )
    }
}

fun Ingredient.asEntity() = com.project.database.model.IngredientEntity(
    name = this.name,
    count = this.count,
    category = this.category,
    enterDate = this.enterDate,
    expirationDate = this.expirationDate,
    store = this.store,
)
