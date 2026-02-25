package com.project.model.ingredient

enum class IngredientCategory(
    val n: String,
    val color: Long,
    val background: Long
) {
    CONDIMENT("양념", 0xFFA67C52, 0xFFF6EFE7),
    VEGETABLE("채소", 0xFFFF8C00, 0xFFFFF3E0),
    FRUIT("과일", 0xFF4CAF50, 0xFFE8F5E9),
    MEAT("육류", 0xFFFF0000, 0xFFFFF3E0),
    DAIRY("유제품", 0xFFFFF8EE, 0xFFFFD6DC),
    GRAIN("곡물", 0xFFC2A16B, 0xFFEFE3CF),
    BEVERAGE("음료", 0xFF2196F3, 0xFFE3F2FD),
    SNACK("간식", 0xFFA0522D, 0xFFDEB887),
    OTHER("기타", 0xFF000000, 0xFFCCCCCC),
}

fun getIndexToIngredientCategory(index: Int?): IngredientCategory =
    index?.let { IngredientCategory.entries[it] } ?: IngredientCategory.OTHER
