package com.project.recipe.recipinfo

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.project.ingredient.usecase.recipe.GetRecipeInfoUseCase
import com.project.navigation.IngredientRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRecipeInfoUseCase: GetRecipeInfoUseCase,
): ViewModel() {
    private val route: IngredientRoute.RecipeInfo = savedStateHandle.toRoute()

    init {
        viewModelScope.launch {
            val info = getRecipeInfoUseCase.invoke(route.recipeId)
            Log.e("Test", info.toString())
        }
    }
}
