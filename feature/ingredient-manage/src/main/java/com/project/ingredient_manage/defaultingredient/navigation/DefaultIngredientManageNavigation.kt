package com.project.ingredient_manage.defaultingredient.navigation

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.project.ingredient_manage.defaultingredient.DefaultIngredientManageScreen
import com.project.ingredient_manage.defaultingredient.DefaultIngredientManageViewModel
import com.project.ingredient_manage.defaultingredient.contract.DefaultIngredientEffect
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

fun NavGraphBuilder.defaultIngredientManageGraph(
    navigator: IngredientFarmingNavigator,
) {
    composable<IngredientRoute.DefaultIngredientManage> {
        val defaultIngredientViewModel: DefaultIngredientManageViewModel = hiltViewModel()
        val defaultIngredientState by defaultIngredientViewModel.collectAsState()
        val ingredients = defaultIngredientViewModel.ingredients.collectAsLazyPagingItems()
        val context = LocalContext.current

        defaultIngredientViewModel.collectSideEffect { effect ->
            when (effect) {
                is DefaultIngredientEffect.NavigateToBack -> {
                    navigator.navController.popBackStack()
                }

                is DefaultIngredientEffect.UpdateDialogError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        DefaultIngredientManageScreen(
            state = defaultIngredientState,
            ingredients = ingredients,
            onIntent = defaultIngredientViewModel::onIntent
        )
    }
}
