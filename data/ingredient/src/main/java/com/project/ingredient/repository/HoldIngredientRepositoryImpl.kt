package com.project.ingredient.repository

import androidx.room.Transaction
import com.project.database.dao.HoldIngredientDao
import com.project.database.dao.IngredientDao
import com.project.model.ingredient.Ingredient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HoldIngredientRepositoryImpl @Inject constructor(
    private val holdIngredientDao: HoldIngredientDao,
    private val ingredientDao: IngredientDao,
) : HoldIngredientRepository {
    override suspend fun getHoldIngredientById(id: Int): Ingredient {
        return holdIngredientDao.getHoldIngredientById(id)
    }

    override suspend fun updateHoldIngredientCount(id: Int, count: Double) {
        holdIngredientDao.updateHoldIngredientCount(id, count)
    }

    override suspend fun getHoldIngredientCountByIngredientId(id: Int): Double {
        return holdIngredientDao.getHoldIngredientCountByIngredientId(id) ?: 0.0
    }

    override fun getAllHoldIngredient(): Flow<List<Ingredient>> {
        return holdIngredientDao.getAllHoldIngredient()
    }

    override suspend fun deleteHoldIngredientByIds(ids: List<Int>) {
        runWithRetry {
            deleteHoldIngredientAndUpdateHoldState(ids)
        }
    }

    @Transaction
    private suspend fun deleteHoldIngredientAndUpdateHoldState(ids: List<Int>) {
        holdIngredientDao.deleteHoldIngredientsByIds(ids)
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
