package com.project.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.database.model.HoldIngredientEntity
import com.project.database.model.IngredientEntity
import com.project.model.ingredient.ExpirationDateSoonIngredient
import com.project.model.ingredient.Ingredient
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {

    @Query("SELECT * FROM IngredientEntity WHERE name =:name")
    suspend fun getIngredientByName(name: String): IngredientEntity?

    @Query("SELECT id FROM IngredientEntity WHERE name =:name")
    suspend fun findIngredientIdByName(name: String): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(ingredientEntity: IngredientEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHoldIngredient(holdIngredientEntity: HoldIngredientEntity): Long

    @Query("DELETE FROM HoldIngredientEntity WHERE id IN (:ids)")
    suspend fun deleteHoldIngredientsByIds(ids: List<Int>)

    @Query(
        """
        UPDATE IngredientEntity 
        SET hold_state = 0 
        WHERE id NOT IN (SELECT DISTINCT ingredient_id FROM HoldIngredientEntity)
        AND hold_state = 1
    """
    )
    suspend fun updateMissingIngredientsHoldState(): Int


    @Query("SELECT Count(*) FROM IngredientEntity WHERE hold_state = 1")
    fun getIngredientCount(): Flow<Int>

    @Query(
        """
        SELECT Count(*) 
        FROM IngredientEntity 
        JOIN HoldIngredientEntity ON IngredientEntity.id = HoldIngredientEntity.ingredient_id
        WHERE HoldIngredientEntity.expirationDate BETWEEN DATE('now') AND DATE('now', '+3 days')
        """
    )
    fun getExpirationDateSoonCount(): Flow<Int>

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
            IngredientEntity.id,
            name,
            expirationDate,
            category
        FROM IngredientEntity
        JOIN HoldIngredientEntity ON IngredientEntity.id = HoldIngredientEntity.ingredient_id
        WHERE HoldIngredientEntity.expirationDate BETWEEN DATE('now') AND DATE('now', '+3 days')
    """
    )
    fun getExpirationDateSoonIngredient(): Flow<List<ExpirationDateSoonIngredient>>

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
