package com.project.home.navigation

import android.widget.Toast
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
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
    appFinish: () -> Unit,
) {
    composable<IngredientRoute.Home> {
        val viewModel: HomeViewModel = hiltViewModel()
        val homeState by viewModel.collectAsState()
        val snackBarHostState = remember { SnackbarHostState() }
        var backPressedTime by remember { mutableLongStateOf(0L) }
        val context = LocalContext.current

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

                is HomeEffect.AppFinish -> {
                    val currentTime = System.currentTimeMillis()

                    if (currentTime - backPressedTime <= 2000L) {
                        appFinish()
                    } else {
                        backPressedTime = currentTime
                        Toast.makeText(context, "한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                    }
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
