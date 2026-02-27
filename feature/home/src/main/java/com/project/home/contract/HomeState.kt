package com.project.home.contract

import androidx.compose.runtime.Immutable
import com.project.model.ingredient.Ingredient
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class HomeState(
    val ingredientCount: Int = 0,
    val expiresSoonCount: Int = 0,
    val recipeCount: Int = 0,
    val addStatus: Boolean = false,
    val expirationDateSoonItems: ImmutableList<Ingredient> = persistentListOf()
)
