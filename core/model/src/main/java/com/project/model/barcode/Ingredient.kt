package com.project.model.barcode

import java.time.LocalDate

data class Ingredient(
    val id: Int = 0,
    val name: String,
    val count: Int,
    val category: IngredientCategory,
    val enterDate: LocalDate,
    val expirationDate: LocalDate? = null,
    val store: IngredientStore,
)
