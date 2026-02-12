package com.project.model.barcode

enum class IngredientCategory(val n: String) {
    CONDIMENT("양념"),
    VEGETABLE("채소"),
    FRUIT("과일"),
    MEAT("육류"),
    DAIRY("유제품"),
    GRAIN("곡물"),
    BEVERAGE("음료"),
    SNACK("간식"),
    OTHER("기타")
}

fun getIndexToIngredientCategory(index: Int?): IngredientCategory =
    index?.let { IngredientCategory.entries[it] } ?: IngredientCategory.OTHER
