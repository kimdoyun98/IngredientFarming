package com.project.ingredient_manage.update.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.ingredient_manage.update.UpdateHoldIngredientScreen
import com.project.ingredient_manage.update.UpdateHoldIngredientViewModel
import com.project.ingredient_manage.update.contract.UpdateEffect
import com.project.navigation.IngredientRoute
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

fun NavGraphBuilder.updateHoldIngredientGraph(
    popBackStack: () -> Unit,
) {
    composable<IngredientRoute.UpdateHoldIngredient> {
        val updateViewModel: UpdateHoldIngredientViewModel = hiltViewModel()
        val updateState by updateViewModel.collectAsState()

        updateViewModel.collectSideEffect { effect ->
            when (effect) {
                UpdateEffect.PopBackStack -> {
                    popBackStack()
                }
            }
        }

        UpdateHoldIngredientScreen(
            updateState = updateState,
            onIntent = updateViewModel::onIntent,
        )
    }
}
