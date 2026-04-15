package com.project.home.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.home.HomeScreen
import com.project.home.HomeViewModel
import com.project.home.contract.HomeEffect
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

fun NavGraphBuilder.homeGraph(
    navigator: IngredientFarmingNavigator,
) {
    composable<IngredientRoute.Home> {
        val viewModel: HomeViewModel = hiltViewModel()
        val homeState by viewModel.collectAsState()
        val snackBarHostState = remember { SnackbarHostState() }

        viewModel.collectSideEffect { effect ->
            when (effect) {
                is HomeEffect.NavigateToManage -> {
                    navigator.navigateToManage()
                }

                is HomeEffect.NavigateToBarcodeScanner -> {
                    navigator.navigateToBarcodeScanner()
                }

                is HomeEffect.NavigateToDirectInput -> {
                    navigator.navigateToDirectInput()
                }

                is HomeEffect.NavigateToRecipe -> {
                    navigator.navigateToRecipe()
                }

                is HomeEffect.NavigateToShoppingCart -> {
                    navigator.navigateToShoppingCart()
                }
            }

        }

        HomeScreen(
            homeState = homeState,
            onIntent = viewModel::onIntent,
            snackBarHost = snackBarHostState,
        )
    }
}
