package com.project.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.project.database.model.recipe.RecipeEntity
import com.project.database.model.recipe.RecipeIngredientEntity
import com.project.database.model.recipe.RecipeStepEntity
import com.project.database.relation.RecipeInfoRelation
import com.project.database.relation.RecipeWithIngredients
import com.project.model.recipe.RecipeCategory
import com.project.model.recipe.RecipeListItem
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Transaction
    @Query("SELECT * FROM RecipeEntity")
    fun getAllRecipe(): Flow<List<RecipeWithIngredients>>

    @Transaction
    @Query("SELECT * FROM RecipeEntity WHERE id = :recipeId")
    suspend fun getRecipeDetail(recipeId: Int): RecipeInfoRelation

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(entity: RecipeEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeStep(entity: RecipeStepEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipeIngredient(entity: RecipeIngredientEntity)

    @Query(
        """
        SELECT * FROM RecipeEntity
        WHERE (:query IS NULL OR name LIKE '%' || :query || '%')
        AND (:category IS NULL OR category = :category)
        """
    )
    fun getRecipesPagingSource(
        query: String?,
        category: RecipeCategory?
    ): PagingSource<Int, RecipeWithIngredients>
}
