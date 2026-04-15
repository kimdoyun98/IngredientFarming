package com.project.model

import kotlinx.serialization.Serializable

@Serializable
data class RootJson(
    val ingredients: List<IngredientJson>,
    val meat: MeatJson
)

@Serializable
data class IngredientJson(
    val category: String,
    val ingredient: String,
    val store: String
)

@Serializable
data class MeatJson(
    val category: String,
    val types: List<MeatTypeJson>
)

@Serializable
data class MeatTypeJson(
    val name: String,
    val parts: List<MeatPartJson>
)

@Serializable
data class MeatPartJson(
    val name: String,
    val store: String
)
