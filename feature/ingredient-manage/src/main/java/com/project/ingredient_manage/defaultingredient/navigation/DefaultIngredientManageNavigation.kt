package com.project.ingredient_manage.defaultingredient.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute

fun NavGraphBuilder.defaultIngredientManageGraph(
    navigator: IngredientFarmingNavigator,
) {
    composable<IngredientRoute.DefaultIngredientManage>{

    }
}
