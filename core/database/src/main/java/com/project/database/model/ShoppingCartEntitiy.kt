package com.project.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.model.cart.ShoppingCart
import com.project.model.ingredient.IngredientCategory

@Entity(
    tableName = "ShoppingCartEntity",
)
data class ShoppingCartEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val count: Int,
    val category: IngredientCategory,
)

fun ShoppingCartEntity.asExternalModel(): ShoppingCart =
    ShoppingCart(
        id = id,
        name = name,
        count = count,
        category = category,
        success = false
    )
