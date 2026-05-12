package com.project.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.database.model.IngredientStateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientStateDao {

    /**
     * Get
     */
    @Query("SELECT Count(*) FROM IngredientStateEntity WHERE hold_state = 1")
    fun getIngredientCount(): Flow<Int>

    /**
     * Insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredientState(entity: IngredientStateEntity)


    /**
     * Update
     */
    @Query("UPDATE IngredientStateEntity SET hold_state = 1 WHERE ingredientId =:id")
    suspend fun updateHoldStateById(id: Int)

    @Query(
        """
        UPDATE IngredientStateEntity 
        SET hold_state = 0 
        WHERE ingredientId NOT IN (SELECT DISTINCT ingredient_id FROM HoldIngredientEntity)
        AND hold_state = 1
    """
    )
    suspend fun updateMissingIngredientsHoldState(): Int

    @Query("UPDATE IngredientStateEntity SET is_in_complete=1 WHERE ingredientId=:id")
    suspend fun updateCompleteState(id: Int)
}
