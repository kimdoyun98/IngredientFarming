package com.project.ingredient.repository

import androidx.room.Transaction
import com.project.database.dao.IngredientDao
import com.project.database.model.asExternalModel
import com.project.ingredient.asHoldIngredientEntity
import com.project.ingredient.asIngredientEntity
import com.project.model.ingredient.ExpirationDateSoonIngredient
import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientInfo
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class IngredientRepositoryImpl @Inject constructor(
    private val ingredientDao: IngredientDao,
) : IngredientRepository {

    override suspend fun getHoldIngredientById(id: Int): Ingredient {
        return ingredientDao.getHoldIngredientById(id)
    }

    override suspend fun updateHoldIngredientCount(id: Int, count: Int) {
        ingredientDao.updateHoldIngredientCount(id, count)
    }

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

                ingredientDao.insertHoldIngredient(ingredient.asHoldIngredientEntity(id))
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

    override fun getAllHoldIngredient(): Flow<List<Ingredient>> =
        ingredientDao.getAllHoldIngredient()

    override suspend fun deleteHoldIngredientByIds(ids: List<Int>) {
        runWithRetry {
            deleteHoldIngredientAndUpdateHoldState(ids)
        }
    }

    @Transaction
    private suspend fun deleteHoldIngredientAndUpdateHoldState(ids: List<Int>) {
        ingredientDao.deleteHoldIngredientsByIds(ids)
        ingredientDao.updateMissingIngredientsHoldState()
    }

    private suspend fun <T> runWithRetry(
        times: Int = 3,           // 최대 시도 횟수
        initialDelay: Long = 100L, // 시작 대기 시간 (ms)
        block: suspend () -> T    // 실행할 코드 블록
    ): Result<T> {
        var currentDelay = initialDelay
        repeat(times) { attempt ->
            try {
                return Result.success(block())
            } catch (e: Exception) {
                // 마지막 시도까지 실패하면 에러 반환
                if (attempt == times - 1) return Result.failure(e)

                // 재시도 전 대기 (지수 백오프: 시도할수록 대기 시간이 늘어남)
                delay(currentDelay)
                currentDelay *= 2
            }
        }
        return Result.failure(RuntimeException("Unknown error during retry"))
    }
}
