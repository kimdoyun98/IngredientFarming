package com.project.model.ingredient

import java.time.LocalDate

data class ExpirationDateSoonIngredient(
    val id: Int,
    val name: String,
    val expirationDate: LocalDate,
    val category: IngredientCategory
)
