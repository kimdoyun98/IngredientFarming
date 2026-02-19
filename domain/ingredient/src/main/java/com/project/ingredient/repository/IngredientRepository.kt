package com.project.ingredient.repository

import com.project.model.barcode.Ingredient
import kotlinx.coroutines.flow.Flow

interface IngredientRepository {
    suspend fun insertIngredient(igdList: List<Ingredient>): List<Long>
    fun getIngredientCount(): Flow<Int>
    fun getCountExpiringInThreeDays(): Flow<Int>
}
