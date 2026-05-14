package com.project.ingredient.repository

import androidx.paging.PagingData
import com.project.model.ingredient.DefaultIngredient
import com.project.model.ingredient.ExpirationDateSoonIngredient
import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientFilter
import com.project.model.ingredient.IngredientInfo
import com.project.model.ingredient.IngredientStore
import kotlinx.coroutines.flow.Flow

interface IngredientRepository {
    fun getDefaultIngredients(filter: IngredientFilter): Flow<PagingData<DefaultIngredient>>
    suspend fun getIngredientByName(name: String): IngredientInfo?
    suspend fun getIngredientById(id: Int): IngredientInfo

    fun getExpirationDateSoonIngredient(): Flow<List<ExpirationDateSoonIngredient>>
    suspend fun insertIngredient(igdList: List<Ingredient>)
    fun getIngredientCount(): Flow<Int>
    fun getCountExpiringInThreeDays(): Flow<Int>
    suspend fun insertUnknownIngredient(name: String): Int
    suspend fun insertUnknownIngredient(name: String, category: IngredientCategory): Int
    suspend fun updateUnknownIngredient(id: Int, category: IngredientCategory, store: IngredientStore): Result<Unit>
}
