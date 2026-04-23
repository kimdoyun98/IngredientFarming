package com.project.shopping_cart.repository

import com.project.database.dao.ShoppingCartDao
import com.project.database.model.asExternalModel
import com.project.ingredient.repository.ShoppingCartRepository
import com.project.model.cart.ShoppingCart
import com.project.shopping_cart.asShoppingCartEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShoppingCartRepositoryImpl @Inject constructor(
    private val shoppingCartDao: ShoppingCartDao
) : ShoppingCartRepository {
    override suspend fun insertShoppingCartItem(item: ShoppingCart) {
        val cart = getShoppingCartItemByName(item.name)

        if (cart == null) {
            shoppingCartDao.insertShoppingCartItem(item.asShoppingCartEntity())
        }
        else {
            shoppingCartDao.updateShoppingCartItemCount(
                cart.copy(count = cart.count + item.count).asShoppingCartEntity()
            )
        }
    }

    override fun getAllShoppingCartItems(): Flow<List<ShoppingCart>> {
        return shoppingCartDao.getAllShoppingCartItems()
            .map { entities ->
                entities.map {
                    it.asExternalModel()
                }
            }
    }

    override suspend fun deleteShoppingCartItem(item: ShoppingCart) {
        shoppingCartDao.deleteShoppingCartItem(item.asShoppingCartEntity())
    }

    override suspend fun getShoppingCartItemByName(name: String): ShoppingCart? {
        return shoppingCartDao.getShoppingCartItemByName(name)?.asExternalModel()
    }
}
