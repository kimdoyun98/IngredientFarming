package com.project.ingredient.repository

import com.project.model.ingredient.Ingredient
import kotlinx.coroutines.flow.Flow

interface HoldIngredientRepository {
    fun getAllHoldIngredient(): Flow<List<Ingredient>>
    suspend fun deleteHoldIngredientByIds(ids: List<Int>)
    suspend fun getHoldIngredientById(id: Int): Ingredient
    suspend fun updateHoldIngredientCount(id: Int, count: Double)
    suspend fun getHoldIngredientCountByIngredientId(id: Int): Double
}
