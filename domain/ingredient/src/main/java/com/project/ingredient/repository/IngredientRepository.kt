package com.project.ingredient.repository

import com.project.model.barcode.Ingredient

interface IngredientRepository {
    suspend fun insertIngredient(igdList: List<Ingredient>): List<Long>
}
