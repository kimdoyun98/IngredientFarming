package com.project.model.ingredient

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
data class ExpirationDateSoonIngredient(
    val id: Int,
    val name: String,
    val expirationDate: LocalDate,
    val category: IngredientCategory
)
