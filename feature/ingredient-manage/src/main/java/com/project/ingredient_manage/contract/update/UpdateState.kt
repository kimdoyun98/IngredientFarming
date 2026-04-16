package com.project.ingredient_manage.contract.update

import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore
import java.time.LocalDate

data class UpdateState(
    val name: String = Ingredient.UNKNOWN_NAME,
    val count: Double = 0.0,
    val category: IngredientCategory = IngredientCategory.FRUIT,
    val store: IngredientStore = IngredientStore.ROOM_TEMPERATURE,
    val enterDate: LocalDate = LocalDate.now(),
    val expirationDate: LocalDate = LocalDate.now().plusDays(1),
    val step: Double = 1.0
)
