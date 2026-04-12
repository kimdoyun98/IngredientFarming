package com.project.ingredient.repository

import com.project.database.dao.CategoryGroupDao
import com.project.database.dao.IngredientDao
import com.project.database.model.IngredientCategoryGroupEntity
import com.project.database.model.IngredientEntity
import com.project.model.RootJson
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore
import javax.inject.Inject

class DefaultIngredientSettingRepositoryImpl @Inject constructor(
    private val ingredientDao: IngredientDao,
    private val categoryGroupDao: CategoryGroupDao,
): DefaultIngredientSettingRepository {

    override suspend fun prepopulate(
        rootJson: RootJson
    ) {
        rootJson.ingredients.forEach {
            ingredientDao.insertIngredient(
                IngredientEntity(
                    name = it.ingredient,
                    category = IngredientCategory.valueOf(it.category),
                    store = IngredientStore.valueOf(it.store),
                    categoryGroupId = null,
                    holdState = false
                )
            )
        }

        rootJson.meat.types.forEach { type ->
            val groupId = categoryGroupDao.insert(
                IngredientCategoryGroupEntity(
                    groupType = type.name,
                    category = IngredientCategory.MEAT
                )
            ).toInt()

            type.parts.forEach { part ->
                ingredientDao.insertIngredient(
                    IngredientEntity(
                        name = part.name,
                        category = IngredientCategory.MEAT,
                        store = IngredientStore.valueOf(part.store),
                        categoryGroupId = groupId,
                        holdState = false
                    )
                )
            }
        }
    }

    override suspend fun isInitDefaultIngredient(): Boolean {
        return categoryGroupDao.getCategoryGroupCount() > 0
    }
}
