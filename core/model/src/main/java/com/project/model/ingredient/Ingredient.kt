package com.project.model.ingredient

import java.time.LocalDate

data class Ingredient(
    val id: Int = 0,
    val name: String = UNKNOWN_NAME,
    val count: Double = 1.0,
    val category: IngredientCategory = IngredientCategory.OTHER,
    val categoryGroup: String? = null,
    val enterDate: LocalDate = LocalDate.now(),
    val expirationDate: LocalDate = LocalDate.parse(UNKNOWN_DATE),
    val store: IngredientStore = IngredientStore.ROOM_TEMPERATURE,
    val step: Double = 1.0,
) {
    companion object {
        const val UNKNOWN_NAME = "알수없음"
        const val UNKNOWN_DATE = "9999-01-01"
    }
}
