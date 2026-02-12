package com.project.model.barcode

import java.time.LocalDate

data class Ingredient(
    val id: Int = 0,
    val name: String = "알수없음",
    val count: Int = 1,
    val category: IngredientCategory = IngredientCategory.OTHER,
    val enterDate: LocalDate = LocalDate.now(),
    val expirationDate: LocalDate? = null,
    val store: IngredientStore = IngredientStore.ROOM_TEMPERATURE,
)
