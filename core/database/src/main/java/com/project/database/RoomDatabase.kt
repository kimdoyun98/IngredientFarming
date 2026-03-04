package com.project.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.database.dao.HoldIngredientDao
import com.project.database.dao.IngredientDao
import com.project.database.dao.ShoppingCartDao
import com.project.database.model.HoldIngredientEntity
import com.project.database.model.IngredientEntity
import com.project.database.model.ShoppingCartEntity

@Database(
    entities = [
        IngredientEntity::class,
        HoldIngredientEntity::class,
        ShoppingCartEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converter::class)
internal abstract class IngredientFarmingDatabase : RoomDatabase() {
    abstract fun getIngredientDao(): IngredientDao
    abstract fun getHoldIngredientDao(): HoldIngredientDao
    abstract fun getShoppingCardDao(): ShoppingCartDao
}
