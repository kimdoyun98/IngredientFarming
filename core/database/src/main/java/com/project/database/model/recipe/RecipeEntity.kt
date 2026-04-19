package com.project.database.model.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.model.recipe.RecipeCategory

@Entity(tableName = "RecipeEntity")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val imageUri: String,
    val category: RecipeCategory,
    val minute: Int,
    val people: Int
)
