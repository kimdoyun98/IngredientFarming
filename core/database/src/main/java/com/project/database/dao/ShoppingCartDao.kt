package com.project.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.project.database.model.ShoppingCartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingCartDao {

    @Insert
    suspend fun insertShoppingCartItem(item: ShoppingCartEntity)

    @Query("SELECT * FROM ShoppingCartEntity")
    fun getAllShoppingCartItems(): Flow<List<ShoppingCartEntity>>

    @Delete
    suspend fun deleteShoppingCartItem(item: ShoppingCartEntity)
}
