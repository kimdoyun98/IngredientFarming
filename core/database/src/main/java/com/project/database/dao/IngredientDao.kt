package com.project.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.database.model.IngredientEntity
import com.project.model.ingredient.ExpirationDateSoonIngredient
import com.project.model.ingredient.IngredientInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {

    /**
     * GET
     */
    @Query(
        """
        SELECT 
            IngredientEntity.id as id,
            IngredientEntity.name as name,
            IngredientEntity.category as category,
            IngredientCategoryGroupEntity.groupType as categoryGroup,
            IngredientEntity.store as store
        FROM IngredientEntity
        LEFT JOIN IngredientCategoryGroupEntity ON IngredientEntity.group_id = IngredientCategoryGroupEntity.id
        WHERE name =:name
        """
    )
    suspend fun getIngredientByName(name: String): IngredientInfo?

    @Query("SELECT * FROM IngredientEntity WHERE id=:id")
    suspend fun getIngredientById(id: Int): IngredientInfo?

    @Query("SELECT id FROM IngredientEntity WHERE name =:name")
    suspend fun findIngredientIdByName(name: String): Int?

    @Query(
        """
        SELECT Count(*) 
        FROM IngredientEntity 
        JOIN HoldIngredientEntity ON IngredientEntity.id = HoldIngredientEntity.ingredient_id
        WHERE HoldIngredientEntity.expirationDate <= DATE('now', '+3 days')
        """
    )
    fun getExpirationDateSoonCount(): Flow<Int>

    @Query(
        """
        SELECT
            IngredientEntity.id,
            name,
            expirationDate,
            category
        FROM IngredientEntity
        JOIN HoldIngredientEntity ON IngredientEntity.id = HoldIngredientEntity.ingredient_id
        WHERE HoldIngredientEntity.expirationDate <= DATE('now', '+3 days')
    """
    )
    fun getExpirationDateSoonIngredient(): Flow<List<ExpirationDateSoonIngredient>>

    /**
     * INSERT
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(ingredientEntity: IngredientEntity): Long

    /**
     * UPDATE
     */

}
