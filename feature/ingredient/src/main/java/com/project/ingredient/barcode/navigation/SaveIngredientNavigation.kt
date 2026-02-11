package com.project.ingredient.barcode.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.ingredient.barcode.ui.save.SaveIngredientScreen
import com.project.ingredient.barcode.ui.save.SaveIngredientViewModel
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute

fun NavGraphBuilder.saveIngredientGraph(
    navigator: IngredientFarmingNavigator
) {
    composable<IngredientRoute.SaveIngredient>    {
        val saveIngredientViewModel: SaveIngredientViewModel = hiltViewModel()
        saveIngredientViewModel.saveTestData()
        
    }
}
