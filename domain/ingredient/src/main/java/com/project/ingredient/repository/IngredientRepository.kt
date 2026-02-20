package com.project.ingredient.repository

import com.project.model.barcode.Ingredient
import com.project.model.barcode.IngredientCategory
import kotlinx.coroutines.flow.Flow

interface IngredientRepository {
    suspend fun insertIngredient(igdList: List<Ingredient>): List<Long>
    fun getIngredientCount(): Flow<Int>
    fun getCountExpiringInThreeDays(): Flow<Int>

    suspend fun searchIngredients(query: String, category: IngredientCategory?): List<Ingredient>
}
