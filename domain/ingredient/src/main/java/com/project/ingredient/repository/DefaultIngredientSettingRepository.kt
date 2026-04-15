package com.project.ingredient.repository

import com.project.model.RootJson

interface DefaultIngredientSettingRepository {
    suspend fun prepopulate(rootJson: RootJson)

    suspend fun isInitDefaultIngredient(): Boolean
}
