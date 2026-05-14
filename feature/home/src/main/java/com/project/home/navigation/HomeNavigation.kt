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
import com.project.navigation.IngredientRoute
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

fun NavGraphBuilder.homeGraph(
    navigateToManage: () -> Unit,
    navigateToBarcodeScanner: () -> Unit,
    navigateToDirectInput: () -> Unit,
    navigateToRecipe: () -> Unit,
    navigateToShoppingCart: () -> Unit,
) {
    composable<IngredientRoute.Home> {
        val viewModel: HomeViewModel = hiltViewModel()
        val homeState by viewModel.collectAsState()
        val snackBarHostState = remember { SnackbarHostState() }

        viewModel.collectSideEffect { effect ->
            when (effect) {
                is HomeEffect.NavigateToManage -> {
                    navigateToManage()
                }

                is HomeEffect.NavigateToBarcodeScanner -> {
                    navigateToBarcodeScanner()
                }

                is HomeEffect.NavigateToDirectInput -> {
                    navigateToDirectInput()
                }

                is HomeEffect.NavigateToRecipe -> {
                    navigateToRecipe()
                }

                is HomeEffect.NavigateToShoppingCart -> {
                    navigateToShoppingCart()
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
