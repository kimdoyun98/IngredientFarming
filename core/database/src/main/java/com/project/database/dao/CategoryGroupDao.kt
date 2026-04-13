package com.project.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.database.model.IngredientCategoryGroupEntity

@Dao
interface CategoryGroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: IngredientCategoryGroupEntity): Long

    @Query("SELECT id FROM IngredientCategoryGroupEntity WHERE groupType=:group")
    suspend fun getGroupIdByGroup(group: String): Int

    @Query("SELECT Count(*) FROM IngredientCategoryGroupEntity")
    suspend fun getCategoryGroupCount(): Int
}
