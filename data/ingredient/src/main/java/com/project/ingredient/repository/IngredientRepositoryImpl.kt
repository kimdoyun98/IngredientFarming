package com.project.ingredient.repository

import com.project.database.dao.IngredientDao
import com.project.database.model.asExternalModel
import com.project.ingredient.asEntity
import com.project.model.barcode.Ingredient
import com.project.model.barcode.IngredientCategory
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

    override suspend fun searchIngredients(
        query: String,
        category: IngredientCategory?
    ): List<Ingredient> {

        //return ingredientDao.getIngredientsByName(query).map { it.asExternalModel() }
        return ingredientDao.searchIngredients(query, category).map { it.asExternalModel() }
    }
}
