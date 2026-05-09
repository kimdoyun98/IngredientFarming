package com.project.ingredient_manage.defaultingredient.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.project.ingredient_manage.defaultingredient.DefaultIngredientManageViewModel
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

        defaultIngredientViewModel.collectSideEffect { effect ->

        }
    }
}
