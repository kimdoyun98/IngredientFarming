package com.project.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore

@Entity(tableName = "IngredientCategoryGroupEntity")
data class IngredientCategoryGroupEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val groupType: String,
    val category: IngredientCategory
)

@Entity(
    tableName = "IngredientEntity",
    foreignKeys = [
        ForeignKey(
            entity = IngredientCategoryGroupEntity::class,
            parentColumns = ["id"],
            childColumns = ["group_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index("group_id"),
        Index(value = ["name"], unique = true)
    ]
)
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    val name: String,

    val category: IngredientCategory,


    val store: IngredientStore,

    @ColumnInfo(name = "group_id")
    val categoryGroupId: Int? = null,

    @ColumnInfo(name = "hold_state")
    val holdState: Boolean,

    @ColumnInfo(name = "is_auto_decrement")
    val isAutoDecrement: Boolean = true,

    val step: Double = 1.0,
)
