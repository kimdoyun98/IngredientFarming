package com.project.ingredient.repository

import com.project.database.dao.IngredientDao
import com.project.ingredient.asEntity
import com.project.model.barcode.Ingredient
import javax.inject.Inject

class IngredientRepositoryImpl @Inject constructor(
    private val ingredientDao: IngredientDao,
): IngredientRepository {
    override suspend fun insertIngredient(igdList: List<Ingredient>): List<Long> {
        val entities = igdList.map { it.asEntity() }.toTypedArray()
        return ingredientDao.insertIngredient(*entities)
    }
}
