package com.project.model.ingredient

data class DefaultIngredient(
    val id: Int = -1,
    val name: String = "",
    val category: IngredientCategory = IngredientCategory.OTHER,
    val categoryGroup: String? = null,
    val store: IngredientStore = IngredientStore.ROOM_TEMPERATURE,
    val isComplete: Boolean = true,
)
