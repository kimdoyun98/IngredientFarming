package com.project.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.database.model.HoldIngredientEntity
import com.project.model.ingredient.Ingredient
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface HoldIngredientDao {

    /**
     * GET
     */
    @Query(
        """
        SELECT
         HoldIngredientEntity.id as id,
         IngredientEntity.name as name,
         HoldIngredientEntity.count as count,
         IngredientEntity.category as category,
         IngredientCategoryGroupEntity.groupType as categoryGroup,
         IngredientEntity.store as store,
         HoldIngredientEntity.enterDate as enterDate,
         HoldIngredientEntity.expirationDate as expirationDate,
         IngredientEntity.step as step
        FROM IngredientEntity
        JOIN HoldIngredientEntity ON HoldIngredientEntity.ingredient_id = IngredientEntity.id
        LEFT JOIN IngredientCategoryGroupEntity ON IngredientEntity.group_id = IngredientCategoryGroupEntity.id
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
         IngredientCategoryGroupEntity.groupType as categoryGroup,
         IngredientEntity.store as store,
         HoldIngredientEntity.enterDate as enterDate,
         HoldIngredientEntity.expirationDate as expirationDate,
         IngredientEntity.step as step
        FROM HoldIngredientEntity
        JOIN IngredientEntity ON HoldIngredientEntity.ingredient_id = IngredientEntity.id
        LEFT JOIN IngredientCategoryGroupEntity ON IngredientEntity.group_id = IngredientCategoryGroupEntity.id
        WHERE HoldIngredientEntity.id =:id
    """
    )
    suspend fun getHoldIngredientById(id: Int): Ingredient

    @Query(
        """
            SELECT SUM(count)
            FROM HoldIngredientEntity
            WHERE ingredient_id =:id AND expirationDate >= :today
        """
    )
    suspend fun getHoldIngredientCountByIngredientId(
        id: Int,
        today: String = LocalDate.now().toString()
    ): Double?

    /**
     * INSERT
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHoldIngredient(holdIngredientEntity: HoldIngredientEntity): Long

    /**
     * UPDATE
     */
    @Query(
        """
        UPDATE HoldIngredientEntity 
        SET count =:count
        WHERE id =:id
    """
    )
    suspend fun updateHoldIngredientCount(id: Int, count: Double)

    /**
     * DELETE
     */
    @Query("DELETE FROM HoldIngredientEntity WHERE id IN (:ids)")
    suspend fun deleteHoldIngredientsByIds(ids: List<Int>)
}
