package com.project.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.project.database.model.ShoppingCartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingCartDao {

    /**
     * GET
     */
    @Query("SELECT * FROM ShoppingCartEntity")
    fun getAllShoppingCartItems(): Flow<List<ShoppingCartEntity>>

    /**
     * INSERT
     */
    @Insert
    suspend fun insertShoppingCartItem(item: ShoppingCartEntity)

    /**
     * DELETE
     */
    @Delete
    suspend fun deleteShoppingCartItem(item: ShoppingCartEntity)
}
