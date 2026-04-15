package com.project.model.ingredient

enum class IngredientStore(val title: String) {
    ROOM_TEMPERATURE("실온"),
    REFRIGERATED("냉장"),
    FROZEN("냉동");
}

fun getIndexToIngredientStore(index: Int?): IngredientStore =
    index?.let { IngredientStore.entries[it] } ?: IngredientStore.ROOM_TEMPERATURE

fun getIndexByIngredientStore(store: IngredientStore): Int =
    IngredientStore.entries.indexOf(store)

fun getIngredientStore(name: String): IngredientStore {
    return try {
        IngredientStore.valueOf(name)
    } catch (e: IllegalArgumentException) {
        when (name) {
            "실온" -> IngredientStore.ROOM_TEMPERATURE
            "냉장" -> IngredientStore.REFRIGERATED
            "냉동" -> IngredientStore.FROZEN
            else -> throw e
        }
    }
}
