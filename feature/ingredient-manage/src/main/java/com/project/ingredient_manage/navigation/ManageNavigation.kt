package com.project.ingredient_manage.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.ingredient_manage.ManageScreen
import com.project.ingredient_manage.ManageViewModel
import com.project.ingredient_manage.contract.ManageEffect
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

fun NavGraphBuilder.manageGraph(
    navigator: IngredientFarmingNavigator,
) {
    composable<IngredientRoute.Manage> {
        val manageViewModel: ManageViewModel = hiltViewModel()
        val manageState by manageViewModel.collectAsState()

        manageViewModel.collectSideEffect { effect ->
            when (effect) {
                is ManageEffect.NavigateToHome -> {
                    navigator.navigateToHome()
                }
            }
        }

        ManageScreen(
            manageState = { manageState },
            onIntent = manageViewModel::onIntent
        )
    }
}
