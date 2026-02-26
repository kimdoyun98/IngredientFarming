package com.project.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore

@Entity(tableName = "IngredientEntity")
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val category: IngredientCategory,
    val store: IngredientStore,
    @ColumnInfo(name = "hold_state") val holdState: Boolean
)

fun IngredientEntity.asExternalModel(): Ingredient {
    return Ingredient(
        id = id,
        name = name,
        category = category,
        store = store,
    )
}
