package com.project.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.database.model.HoldIngredientEntity
import com.project.model.ingredient.Ingredient
import kotlinx.coroutines.flow.Flow

@Dao
interface HoldIngredientDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHoldIngredient(holdIngredientEntity: HoldIngredientEntity): Long

    @Query("DELETE FROM HoldIngredientEntity WHERE id IN (:ids)")
    suspend fun deleteHoldIngredientsByIds(ids: List<Int>)

    @Query(
        """
        SELECT
         HoldIngredientEntity.id as id,
         IngredientEntity.name as name,
         HoldIngredientEntity.count as count,
         IngredientEntity.category as category,
         IngredientEntity.store as store,
         HoldIngredientEntity.enterDate as enterDate,
         HoldIngredientEntity.expirationDate as expirationDate
        FROM IngredientEntity
        JOIN HoldIngredientEntity ON HoldIngredientEntity.ingredient_id = IngredientEntity.id
    """
    )
    fun getAllHoldIngredient(): Flow<List<Ingredient>>

    @Query(
        """
        SELECT
         HoldIngredientEntity.id as id,
         IngredientEntity.name as name,
         HoldIngredientEntity.count as count,
         IngredientEntity.category as category,
         IngredientEntity.store as store,
         HoldIngredientEntity.enterDate as enterDate,
         HoldIngredientEntity.expirationDate as expirationDate
        FROM HoldIngredientEntity
        JOIN IngredientEntity ON HoldIngredientEntity.ingredient_id = IngredientEntity.id
        WHERE HoldIngredientEntity.id =:id
    """
    )
    suspend fun getHoldIngredientById(id: Int): Ingredient

    @Query(
        """
        UPDATE HoldIngredientEntity 
        SET count =:count
        WHERE id =:id
    """
    )
    suspend fun updateHoldIngredientCount(id: Int, count: Int)
}
