package com.project.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.project.model.cart.ShoppingCart

@Entity(
    tableName = "ShoppingCartEntity",
    foreignKeys = [
        ForeignKey(
            entity = IngredientEntity::class,
            parentColumns = ["id"],
            childColumns = ["ingredientId"],
            onDelete = ForeignKey.SET_DEFAULT//?
        )
    ],
)
data class ShoppingCartEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ingredientId: Int,
    val count: Double,
)

fun ShoppingCartEntity.asExternalModel(): ShoppingCart =
    ShoppingCart(
        id = id,
        ingredientId = ingredientId,
        count = count,
    )
