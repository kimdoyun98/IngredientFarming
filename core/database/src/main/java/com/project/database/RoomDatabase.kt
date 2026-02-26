package com.project.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.database.dao.IngredientDao
import com.project.database.model.HoldIngredientEntity
import com.project.database.model.IngredientEntity

@Database(
    entities = [
        IngredientEntity::class,
        HoldIngredientEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converter::class)
internal abstract class IngredientFarmingDatabase : RoomDatabase() {
    abstract fun getIngredientDao(): IngredientDao
}
