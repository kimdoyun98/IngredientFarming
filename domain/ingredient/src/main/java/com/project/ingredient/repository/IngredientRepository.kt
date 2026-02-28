package com.project.ingredient.repository

import com.project.model.ingredient.ExpirationDateSoonIngredient
import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientInfo
import kotlinx.coroutines.flow.Flow

interface IngredientRepository {
    suspend fun getIngredientByName(name: String): IngredientInfo?

    fun getExpirationDateSoonIngredient(): Flow<List<ExpirationDateSoonIngredient>>
    suspend fun insertIngredient(igdList: List<Ingredient>)
    fun getIngredientCount(): Flow<Int>
    fun getCountExpiringInThreeDays(): Flow<Int>

    suspend fun searchIngredients(query: String, category: IngredientCategory?): List<Ingredient>

    suspend fun deleteHoldIngredientByIds(ids: List<Int>)
}
