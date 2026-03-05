package com.project.home.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.home.HomeScreen
import com.project.home.HomeViewModel
import com.project.home.R
import com.project.home.contract.HomeEffect
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

fun NavGraphBuilder.homeGraph(
    navigator: IngredientFarmingNavigator,
) {
    composable<IngredientRoute.Home> {
        val viewModel: HomeViewModel = hiltViewModel()
        val homeState by viewModel.collectAsState()
        val scope = rememberCoroutineScope()
        val snackBarHostState = remember { SnackbarHostState() }
        val recipeSnackBarMessage = stringResource(R.string.recipe_feature_not_yet)

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
                    //navigator.navigateToRecipe()
                    scope.launch {
                        snackBarHostState
                            .showSnackbar(
                                message = recipeSnackBarMessage,
                                duration = SnackbarDuration.Short
                            )
                    }
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
