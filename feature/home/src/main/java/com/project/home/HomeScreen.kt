package com.project.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.home.component.NavigateButtonsContent
import com.project.home.component.StatusCardsContent
import com.project.home.contract.HomeIntent
import com.project.home.contract.HomeState
import com.project.ui.AddTypeSelectBottomSheetContent
import com.project.ui.AppBarType
import com.project.ui.IngredientFarmingTopAppBar
import kotlinx.coroutines.launch

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    homeState: () -> HomeState,
    onIntent: (HomeIntent) -> Unit,
) {
    HomeScreen(
        modifier = modifier,
        ingredientCount = homeState().ingredientCount,
        expiresSoonCount = homeState().expiresSoonCount,
        recipeCount = homeState().recipeCount,
        addStatus = homeState().addStatus,
        onManageButtonClick = { onIntent(HomeIntent.OnManageButtonClick) },
        onAddButtonClick = { onIntent(HomeIntent.OnAddButtonClick) },
        onDismissRequestToAdd = { onIntent(HomeIntent.OnDismissRequestToAdd) },
        onDirectInputButtonClick = { onIntent(HomeIntent.OnDirectInputButtonClick) },
        onBarcodeScannerButtonClick = { onIntent(HomeIntent.OnBarcodeScannerButtonClick) },
        onRecipeButtonClick = { onIntent(HomeIntent.OnRecipeButtonClick) },
        onShoppingCartButtonClick = { onIntent(HomeIntent.OnShoppingCartButtonClick) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    ingredientCount: Int,
    expiresSoonCount: Int,
    recipeCount: Int,
    addStatus: Boolean = false,
    onManageButtonClick: () -> Unit,
    onAddButtonClick: () -> Unit,
    onDismissRequestToAdd: () -> Unit,
    onDirectInputButtonClick: () -> Unit,
    onBarcodeScannerButtonClick: () -> Unit,
    onRecipeButtonClick: () -> Unit,
    onShoppingCartButtonClick: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    IngredientFarmingTopAppBar(
        title = stringResource(R.string.home_title),
        type = AppBarType.None
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatusCardsContent(
                ingredientCount = ingredientCount,
                expiresSoonCount = expiresSoonCount,
                recipeCount = recipeCount
            )

            NavigateButtonsContent(
                modifier = modifier,
                onManageButtonClick = { onManageButtonClick() },
                onAddButtonClick = { onAddButtonClick() },
                onRecipeButtonClick = { onRecipeButtonClick() },
                onShoppingCartButtonClick = { onShoppingCartButtonClick() }
            )
        }
    }

    if (addStatus) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { onDismissRequestToAdd() }
        ) {
            AddTypeSelectBottomSheetContent(
                modifier = modifier,
                onBarcodeScannerClick = {
                    onBarcodeScannerButtonClick()
                    scope.launch { sheetState.hide() }
                },
                onDirectInputClick = {
                    onDirectInputButtonClick()
                    scope.launch { sheetState.hide() }
                }
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        addStatus = false,
        ingredientCount = 30,
        expiresSoonCount = 5,
        recipeCount = 10,
        onManageButtonClick = { },
        onAddButtonClick = { },
        onDismissRequestToAdd = {},
        onDirectInputButtonClick = { },
        onBarcodeScannerButtonClick = {},
        onRecipeButtonClick = {},
        onShoppingCartButtonClick = { }
    )
}
