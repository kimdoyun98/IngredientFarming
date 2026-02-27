package com.project.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.home.component.ExpirationDateSoonBox
import com.project.home.component.NavigateButtonsContent
import com.project.home.component.StatusCardsContent
import com.project.home.contract.HomeIntent
import com.project.home.contract.HomeState
import com.project.model.ingredient.ExpirationDateSoonIngredient
import com.project.model.ingredient.IngredientCategory
import com.project.ui.AddTypeSelectBottomSheetContent
import com.project.ui.AppBarType
import com.project.ui.IngredientFarmingTopAppBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    homeState: HomeState,
    onIntent: (HomeIntent) -> Unit,
) {
    HomeScreen(
        modifier = modifier,
        ingredientCount = homeState.ingredientCount,
        expiresSoonCount = homeState.expiresSoonCount,
        recipeCount = homeState.recipeCount,
        addStatus = homeState.addStatus,
        expirationDateSoonItems = homeState.expirationDateSoonItems,
        onManageButtonClick = { onIntent(HomeIntent.OnManageButtonClick) },
        onAddButtonClick = { onIntent(HomeIntent.OnAddButtonClick) },
        onDismissRequestToAdd = { onIntent(HomeIntent.OnDismissRequestToAdd) },
        onDirectInputButtonClick = { onIntent(HomeIntent.OnDirectInputButtonClick) },
        onBarcodeScannerButtonClick = { onIntent(HomeIntent.OnBarcodeScannerButtonClick) },
        onRecipeButtonClick = { onIntent(HomeIntent.OnRecipeButtonClick) },
        onShoppingCartButtonClick = { onIntent(HomeIntent.OnShoppingCartButtonClick) },
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    ingredientCount: Int,
    expiresSoonCount: Int,
    recipeCount: Int,
    addStatus: Boolean = false,
    expirationDateSoonItems: ImmutableList<ExpirationDateSoonIngredient>,
    onManageButtonClick: () -> Unit,
    onAddButtonClick: () -> Unit,
    onDismissRequestToAdd: () -> Unit,
    onDirectInputButtonClick: () -> Unit,
    onBarcodeScannerButtonClick: () -> Unit,
    onRecipeButtonClick: () -> Unit,
    onShoppingCartButtonClick: () -> Unit,
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

            ExpirationDateSoonContent(
                modifier = modifier,
                items = expirationDateSoonItems
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

@Composable
private fun ExpirationDateSoonContent(
    modifier: Modifier = Modifier,
    items: ImmutableList<ExpirationDateSoonIngredient>
) {
    Text(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
        text = stringResource(R.string.expiration_date_soon_ingredient),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )

    ExpirationDateSoonBox(
        modifier = modifier,
        items = items
    )
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen(
        homeState = HomeState(
            ingredientCount = 30,
            expiresSoonCount = 5,
            recipeCount = 10,
            expirationDateSoonItems =
                persistentListOf(
                    ExpirationDateSoonIngredient(
                        id = 0,
                        name = "사과",
                        category = IngredientCategory.FRUIT,
                        expirationDate = LocalDate.now().plusDays(2)
                    ),
                    ExpirationDateSoonIngredient(
                        id = 1,
                        name = "삼겹살",
                        category = IngredientCategory.MEAT,
                        expirationDate = LocalDate.now().plusDays(1)
                    )
                )
        ),
        onIntent = {}
    )
}
