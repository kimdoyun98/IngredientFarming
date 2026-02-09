package com.project.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.model.barcode.IngredientCategory
import java.time.LocalDate

@Entity(tableName = "IngredientEntity")
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val count: Int,
    val category: IngredientCategory,
    val enterDate: LocalDate,
    val expirationDate: LocalDate?,
    val store: String?,
)
