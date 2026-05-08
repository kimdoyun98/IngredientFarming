package com.project.ingredient.repository

import androidx.room.Transaction
import com.project.database.dao.CategoryGroupDao
import com.project.database.dao.IngredientDao
import com.project.database.dao.IngredientStateDao
import com.project.database.model.IngredientStateEntity
import com.project.ingredient.asIngredientCategoryGroupEntity
import com.project.ingredient.asIngredientEntity
import com.project.model.IngredientJson
import com.project.model.MeatTypeJson
import com.project.model.RootJson
import com.project.model.ingredient.IngredientCategory
import javax.inject.Inject

class DefaultIngredientSettingRepositoryImpl @Inject constructor(
    private val ingredientDao: IngredientDao,
    private val ingredientStateDao: IngredientStateDao,
    private val categoryGroupDao: CategoryGroupDao,
) : DefaultIngredientSettingRepository {

    override suspend fun prepopulate(
        rootJson: RootJson
    ) {
        rootJson.ingredients.forEach { ingredientJson ->
            insertIngredientsJson(ingredientJson)
        }

        rootJson.meat.types.forEach { type ->
            insertMeatJson(type)
        }
    }

    override suspend fun isInitDefaultIngredient(): Boolean {
        return categoryGroupDao.getCategoryGroupCount() > 0
    }

    @Transaction
    private suspend fun insertIngredientsJson(ingredientJson: IngredientJson) {
        val isAutoDecrement = !(ingredientJson.category == IngredientCategory.CONDIMENT.name ||
                ingredientJson.category == IngredientCategory.GRAIN.name)

        val id = ingredientDao.insertIngredient(
            ingredientJson.asIngredientEntity(
                autoDecrement = isAutoDecrement
            )
        ).toInt()

        insertIngredientState(id)
    }

    @Transaction
    private suspend fun insertMeatJson(type: MeatTypeJson) {
        val groupId = categoryGroupDao.insert(
            type.asIngredientCategoryGroupEntity()
        ).toInt()

        type.parts.forEach { part ->
            val id = ingredientDao.insertIngredient(
                part.asIngredientEntity(groupId)
            ).toInt()

            insertIngredientState(id)
        }
    }

    private suspend fun insertIngredientState(id: Int){
        ingredientStateDao.insertIngredientState(
            IngredientStateEntity(
                ingredientId = id,
                holdState = false,
                isInComplete = true
            )
        )
    }
}
