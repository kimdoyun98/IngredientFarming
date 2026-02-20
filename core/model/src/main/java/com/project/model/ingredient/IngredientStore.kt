package com.project.model.ingredient

enum class IngredientStore(val n: String) {
    ROOM_TEMPERATURE("실온"),
    REFRIGERATED("냉장"),
    FROZEN("냉동");
}

fun getIndexToIngredientStore(index: Int?): IngredientStore =
    index?.let { IngredientStore.entries[it] } ?: IngredientStore.ROOM_TEMPERATURE
