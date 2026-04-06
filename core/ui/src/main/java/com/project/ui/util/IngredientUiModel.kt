package com.project.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore
import kotlinx.collections.immutable.toImmutableList

@Composable
fun rememberIngredientCategoryImmutableList() = remember(IngredientCategory.entries) {
    IngredientCategory.entries.toList().map { it.title }.toImmutableList()
}

@Composable
fun rememberIngredientStoreImmutableList() = remember(IngredientStore.entries) {
    IngredientStore.entries.toList().map { it.title }.toImmutableList()
}
