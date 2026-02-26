package com.project.ingredient

import com.project.database.model.HoldIngredientEntity
import com.project.database.model.IngredientEntity
import com.project.model.barcode.Product
import com.project.model.ingredient.Ingredient
import com.project.network.retrofit.model.BarcodeResponse

fun BarcodeResponse.asExternalModel(): List<Product> {
    return this.i2570.row.map {
        Product(
            barcode = it.brcd_no,
            name = it.prdt_nm
        )
    }
}

fun Ingredient.asIngredientEntity() = IngredientEntity(
    name = name,
    category = category,
    store = store,
    holdState = true
)

fun Ingredient.asHoldIngredientEntity(id: Int) = HoldIngredientEntity(
    ingredientId = id,
    count = count,
    enterDate = enterDate,
    expirationDate = expirationDate,
)
