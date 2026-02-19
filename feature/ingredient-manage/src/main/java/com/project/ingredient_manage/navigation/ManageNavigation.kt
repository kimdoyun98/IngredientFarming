package com.project.ingredient_manage.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.ingredient_manage.ManageScreen
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute

fun NavGraphBuilder.manageGraph(
    navigator: IngredientFarmingNavigator,
) {
    composable<IngredientRoute.Manage> {

        ManageScreen()
    }
}
