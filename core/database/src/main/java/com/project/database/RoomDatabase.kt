package com.project.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.database.dao.CategoryGroupDao
import com.project.database.dao.HoldIngredientDao
import com.project.database.dao.IngredientDao
import com.project.database.dao.RecipeDao
import com.project.database.dao.ShoppingCartDao
import com.project.database.model.HoldIngredientEntity
import com.project.database.model.IngredientCategoryGroupEntity
import com.project.database.model.IngredientEntity
import com.project.database.model.ShoppingCartEntity
import com.project.database.model.recipe.RecipeEntity
import com.project.database.model.recipe.RecipeIngredientEntity
import com.project.database.model.recipe.RecipeStepEntity

@Database(
    entities = [
        IngredientCategoryGroupEntity::class,
        IngredientEntity::class,
        HoldIngredientEntity::class,
        ShoppingCartEntity::class,
        RecipeEntity::class,
        RecipeIngredientEntity::class,
        RecipeStepEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converter::class)
internal abstract class IngredientFarmingDatabase : RoomDatabase() {
    abstract fun getIngredientDao(): IngredientDao
    abstract fun getCategoryGroupDao(): CategoryGroupDao
    abstract fun getHoldIngredientDao(): HoldIngredientDao
    abstract fun getShoppingCardDao(): ShoppingCartDao

    abstract fun getRecipeDao(): RecipeDao
}
