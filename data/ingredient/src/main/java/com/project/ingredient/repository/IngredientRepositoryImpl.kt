package com.project.ingredient.repository

import com.project.database.dao.IngredientDao
import com.project.ingredient.asEntity
import com.project.model.barcode.Ingredient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IngredientRepositoryImpl @Inject constructor(
    private val ingredientDao: IngredientDao,
) : IngredientRepository {
    override suspend fun insertIngredient(igdList: List<Ingredient>): List<Long> {
        val entities = igdList.map { it.asEntity() }.toTypedArray()
        return ingredientDao.insertIngredient(*entities)
    }

    override fun getIngredientCount(): Flow<Int> {
        return ingredientDao.getIngredientCount()
    }


    override fun getCountExpiringInThreeDays(): Flow<Int> {
        return ingredientDao.getExpirationDateSoonCount()
    }
}
