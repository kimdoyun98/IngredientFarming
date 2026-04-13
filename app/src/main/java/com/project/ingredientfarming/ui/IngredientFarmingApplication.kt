package com.project.ingredientfarming.ui

import android.app.Application
import com.project.common.coroutine.di.DispatcherIO
import com.project.ingredient.repository.DefaultIngredientSettingRepository
import com.project.model.RootJson
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltAndroidApp
class IngredientFarmingApplication : Application() {

    @Inject
    lateinit var repository: DefaultIngredientSettingRepository

    @Inject
    @DispatcherIO
    lateinit var scope: CoroutineScope

    override fun onCreate() {
        super.onCreate()

        scope.launch {
            if(!repository.isInitDefaultIngredient()){
                repository.prepopulate(loadJson())
            }
        }
    }

    private fun loadJson(): RootJson {
        val json = Json { ignoreUnknownKeys = true }
        val jsonString = assets.open("ingredients.json")
            .bufferedReader()
            .use { it.readText() }

        return json.decodeFromString(jsonString)
    }
}
