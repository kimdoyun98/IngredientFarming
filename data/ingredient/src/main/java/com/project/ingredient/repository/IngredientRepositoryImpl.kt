package com.project.ingredient.repository

import com.project.database.dao.IngredientDao
import com.project.database.model.asExternalModel
import com.project.ingredient.asHoldIngredientEntity
import com.project.ingredient.asIngredientEntity
import com.project.model.ingredient.ExpirationDateSoonIngredient
import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientInfo
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class IngredientRepositoryImpl @Inject constructor(
    private val ingredientDao: IngredientDao,
) : IngredientRepository {

    override suspend fun getIngredientByName(name: String): IngredientInfo? {
        return ingredientDao.getIngredientByName(name)?.asExternalModel()
    }

    override fun getExpirationDateSoonIngredient(): Flow<List<ExpirationDateSoonIngredient>> {
        return ingredientDao.getExpirationDateSoonIngredient()
    }

    override suspend fun insertIngredient(
        igdList: List<Ingredient>
    ) = coroutineScope {
        igdList.forEach { igd ->
            launch {
                val id = ingredientDao.findIngredientIdByName(igd.name) ?: insertIngredient(igd)

                ingredientDao.insertHoldIngredient(igd.asHoldIngredientEntity(id))
            }
        }
    }

    private suspend fun insertIngredient(igd: Ingredient): Int {
        ingredientDao.insertIngredient(igd.asIngredientEntity())

        return ingredientDao.findIngredientIdByName(igd.name)!!
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

        return ingredientDao.searchIngredients(query, category)
    }

    override suspend fun deleteHoldIngredientByIds(ids: List<Int>) {
        return ingredientDao.deleteHoldIngredientsByIds(ids)
    }
}
