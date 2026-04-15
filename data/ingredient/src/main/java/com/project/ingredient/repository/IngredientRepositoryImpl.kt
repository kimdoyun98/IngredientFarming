package com.project.ingredient.repository

import com.project.database.dao.HoldIngredientDao
import com.project.database.dao.IngredientDao
import com.project.database.model.asExternalModel
import com.project.ingredient.asHoldIngredientEntity
import com.project.ingredient.asIngredientEntity
import com.project.model.ingredient.ExpirationDateSoonIngredient
import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientInfo
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class IngredientRepositoryImpl @Inject constructor(
    private val ingredientDao: IngredientDao,
    private val holdIngredientDao: HoldIngredientDao,
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
        igdList.forEach { ingredient ->
            launch {
                var id = ingredientDao.findIngredientIdByName(ingredient.name)

                if (id == null) id = insertIngredient(ingredient)
                else ingredientDao.updateHoldStateById(id)

                holdIngredientDao.insertHoldIngredient(ingredient.asHoldIngredientEntity(id))
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
}
