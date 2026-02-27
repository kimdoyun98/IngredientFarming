package com.project.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.database.model.HoldIngredientEntity
import com.project.database.model.IngredientEntity
import com.project.model.ingredient.ExpirationDateSoonIngredient
import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientCategory
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
        WHERE
            (:query = '' OR name LIKE '%' || :query || '%')
            AND
            (:category IS NULL OR category = :category)
    """
    )
    suspend fun searchIngredients(
        query: String,
        category: IngredientCategory?
    ): List<Ingredient>

    @Query("""
        SELECT
            IngredientEntity.id,
            name,
            expirationDate,
            category
        FROM IngredientEntity
        JOIN HoldIngredientEntity ON IngredientEntity.id = HoldIngredientEntity.ingredient_id
        WHERE HoldIngredientEntity.expirationDate BETWEEN DATE('now') AND DATE('now', '+3 days')
    """)
    fun getExpirationDateSoonIngredient(): Flow<List<ExpirationDateSoonIngredient>>
}
