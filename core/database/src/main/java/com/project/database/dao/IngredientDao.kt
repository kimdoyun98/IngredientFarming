package com.project.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.database.model.IngredientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(vararg ingredientEntity: IngredientEntity): List<Long>

    @Query("SELECT Count(*) FROM IngredientEntity")
    fun getIngredientCount(): Flow<Int>

    @Query("""
        SELECT Count(*) 
        FROM IngredientEntity 
        WHERE expirationDate BETWEEN DATE('now') AND DATE('now', '+3 days')
        """)
    fun getExpirationDateSoonCount(): Flow<Int>

}
