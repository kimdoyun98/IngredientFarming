package com.project.ingredient

import com.project.database.model.HoldIngredientEntity
import com.project.database.model.IngredientCategoryGroupEntity
import com.project.database.model.IngredientEntity
import com.project.model.IngredientJson
import com.project.model.MeatPartJson
import com.project.model.MeatTypeJson
import com.project.model.barcode.Product
import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore
import com.project.model.ingredient.getIngredientCategory
import com.project.model.ingredient.getIngredientStore
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

fun IngredientJson.asIngredientEntity(autoDecrement: Boolean = true) = IngredientEntity(
    name = ingredient,
    category = getIngredientCategory(category),
    store = getIngredientStore(store),
    categoryGroupId = null,
    holdState = false,
    isAutoDecrement = autoDecrement,
    step = if(autoDecrement) 1.0 else 0.5
)

fun MeatTypeJson.asIngredientCategoryGroupEntity() = IngredientCategoryGroupEntity(
    groupType = name,
    category = IngredientCategory.MEAT
)

fun MeatPartJson.asIngredientEntity(groupId: Int) = IngredientEntity(
    name = name,
    category = IngredientCategory.MEAT,
    store = IngredientStore.valueOf(store),
    categoryGroupId = groupId,
    holdState = false
)
