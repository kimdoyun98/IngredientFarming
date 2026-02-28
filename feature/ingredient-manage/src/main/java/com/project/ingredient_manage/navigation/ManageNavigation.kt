package com.project.ingredient_manage.navigation

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.ingredient_manage.ManageScreen
import com.project.ingredient_manage.ManageViewModel
import com.project.ingredient_manage.contract.ManageEffect
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

fun NavGraphBuilder.manageGraph(
    navigator: IngredientFarmingNavigator,
) {
    composable<IngredientRoute.Manage> {
        val manageViewModel: ManageViewModel = hiltViewModel()
        val manageState by manageViewModel.collectAsState()
        val scope = rememberCoroutineScope()
        val snackBarHostState = remember { SnackbarHostState() }

        manageViewModel.collectSideEffect { effect ->
            when (effect) {
                is ManageEffect.NavigateToHome -> {
                    navigator.navigateToHome()
                }

                is ManageEffect.NavigateToUpdateIngredient -> {
                    navigator.navigateToUpdateHoldIngredient(effect.id)
                }

                is ManageEffect.ShowSnackBar -> {
                    scope.launch {
                        val result = snackBarHostState
                            .showSnackbar(
                                message = "장보기에 추가하시겠습니까?",
                                actionLabel = "추가",
                                withDismissAction = true,
                                duration = SnackbarDuration.Indefinite
                            )
                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                //TODO 추가
                            }

                            SnackbarResult.Dismissed -> {

                            }
                        }
                    }
                }
            }
        }

        ManageScreen(
            manageState = manageState,
            onIntent = manageViewModel::onIntent,
            snackBarHostState = snackBarHostState,
        )
    }
}
