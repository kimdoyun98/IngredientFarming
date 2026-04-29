package com.project.ingredient.repository

import com.project.database.dao.CategoryGroupDao
import com.project.database.dao.IngredientDao
import com.project.ingredient.asIngredientCategoryGroupEntity
import com.project.ingredient.asIngredientEntity
import com.project.model.RootJson
import com.project.model.ingredient.IngredientCategory
import javax.inject.Inject

class DefaultIngredientSettingRepositoryImpl @Inject constructor(
    private val ingredientDao: IngredientDao,
    private val categoryGroupDao: CategoryGroupDao,
) : DefaultIngredientSettingRepository {

    override suspend fun prepopulate(
        rootJson: RootJson
    ) {
        rootJson.ingredients.forEach { ingredientJson ->
            val isAutoDecrement = !(ingredientJson.category == IngredientCategory.CONDIMENT.name ||
                    ingredientJson.category == IngredientCategory.GRAIN.name)

            ingredientDao.insertIngredient(
                ingredientJson.asIngredientEntity(
                    autoDecrement = isAutoDecrement
                )
            )
        }

        rootJson.meat.types.forEach { type ->
            val groupId = categoryGroupDao.insert(
                type.asIngredientCategoryGroupEntity()
            ).toInt()

            type.parts.forEach { part ->
                ingredientDao.insertIngredient(
                    part.asIngredientEntity(groupId)
                )
            }
        }
    }

    override suspend fun isInitDefaultIngredient(): Boolean {
        return categoryGroupDao.getCategoryGroupCount() > 0
    }
}
