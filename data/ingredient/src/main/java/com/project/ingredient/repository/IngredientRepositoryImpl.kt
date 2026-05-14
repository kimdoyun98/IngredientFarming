package com.project.ingredient.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.room.Transaction
import androidx.room.withTransaction
import com.project.database.IngredientFarmingDatabase
import com.project.database.dao.HoldIngredientDao
import com.project.database.dao.IngredientDao
import com.project.database.dao.IngredientStateDao
import com.project.database.model.IngredientStateEntity
import com.project.ingredient.asHoldIngredientEntity
import com.project.ingredient.asIngredientEntity
import com.project.ingredient.asUnknownIngredientEntity
import com.project.model.ingredient.DefaultIngredient
import com.project.model.ingredient.ExpirationDateSoonIngredient
import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientFilter
import com.project.model.ingredient.IngredientInfo
import com.project.model.ingredient.IngredientStore
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class IngredientRepositoryImpl @Inject constructor(
    private val db: IngredientFarmingDatabase,
    private val ingredientDao: IngredientDao,
    private val ingredientStateDao: IngredientStateDao,
    private val holdIngredientDao: HoldIngredientDao,
) : IngredientRepository {
    override fun getDefaultIngredients(filter: IngredientFilter): Flow<PagingData<DefaultIngredient>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),

            pagingSourceFactory = {
                ingredientDao.getDefaultIngredients(
                    query = filter.query.takeIf { it.isNotBlank() },
                    category = filter.category
                )
            }
        ).flow
    }

    override suspend fun getIngredientByName(name: String): IngredientInfo? {
        return ingredientDao.getIngredientByName(name)
    }

    override suspend fun getIngredientById(id: Int): IngredientInfo {
        return ingredientDao.getIngredientById(id)!!
    }

    override fun getExpirationDateSoonIngredient(): Flow<List<ExpirationDateSoonIngredient>> {
        return ingredientDao.getExpirationDateSoonIngredient()
    }

    override suspend fun insertIngredient(
        igdList: List<Ingredient>
    ) = coroutineScope {
        igdList.forEach { ingredient ->
            launch {
                db.withTransaction {
                    var id = ingredientDao.findIngredientIdByName(ingredient.name)

                    if (id == null) id = insertIngredient(ingredient)
                    else ingredientStateDao.updateHoldStateById(id)

                    holdIngredientDao.insertHoldIngredient(ingredient.asHoldIngredientEntity(id))
                }
            }
        }
    }

    private suspend fun insertIngredient(igd: Ingredient): Int {
        return db.withTransaction {
            val id = ingredientDao.insertIngredient(igd.asIngredientEntity()).toInt()

            insertIngredientState(id = id, holdState = true, isInComplete = true)

            id
        }
    }

    override fun getIngredientCount(): Flow<Int> {
        return ingredientStateDao.getIngredientCount()
    }


    override fun getCountExpiringInThreeDays(): Flow<Int> {
        return ingredientDao.getExpirationDateSoonCount()
    }

    override suspend fun insertUnknownIngredient(name: String): Int {
        return db.withTransaction {
            val id = ingredientDao.insertIngredient(
                name.asUnknownIngredientEntity()
            ).toInt()

            insertIngredientState(id = id, holdState = false, isInComplete = false)

            id
        }
    }

    override suspend fun insertUnknownIngredient(
        name: String,
        category: IngredientCategory
    ): Int {
        return db.withTransaction {
            val id = ingredientDao.insertIngredient(
                name.asUnknownIngredientEntity(category)
            ).toInt()

            insertIngredientState(id = id, holdState = false, isInComplete = false)

            id
        }
    }

    override suspend fun updateUnknownIngredient(
        id: Int,
        category: IngredientCategory,
        store: IngredientStore
    ): Result<Unit> {
        return runCatching {
            db.withTransaction {
                ingredientDao.updateUnknownIngredient(
                    id = id,
                    category = category,
                    store = store
                )

                ingredientStateDao.updateCompleteState(id)
            }
        }
    }

    private suspend fun insertIngredientState(
        id: Int,
        holdState: Boolean,
        isInComplete: Boolean,
    ) {
        ingredientStateDao.insertIngredientState(
            IngredientStateEntity(
                ingredientId = id,
                holdState = holdState,
                isInComplete = isInComplete
            )
        )
    }
}
