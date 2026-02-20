package com.project.ingredient_manage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.ingredient_manage.contract.ManageIntent
import com.project.ingredient_manage.contract.ManageState
import com.project.model.barcode.Ingredient
import com.project.model.barcode.IngredientCategory
import com.project.model.barcode.IngredientStore
import com.project.ui.AppBarType
import com.project.ui.IngredientCard
import com.project.ui.IngredientFarmingSearchBar
import com.project.ui.IngredientFarmingTopAppBar
import java.time.LocalDate

@Composable
internal fun ManageScreen(
    modifier: Modifier = Modifier,
    manageState: () -> ManageState,
    onIntent: (ManageIntent) -> Unit,
) {
    ManageScreen(
        modifier = modifier,
        query = manageState().query,
        ingredientItems = manageState().ingredientItems,
        selectedCategoryIndex = manageState().selectedCategoryIndex,
        onClickTopAppBarNavigation = { onIntent(ManageIntent.OnClickTopAppBarNavigation) },
        onClickTopAppBarAction = { onIntent(ManageIntent.OnClickTopAppBarAction) },
        onSearchQueryChange = { q -> onIntent(ManageIntent.OnSearchQueryChange(q)) },
        onSearchCloseButtonClick = { onIntent(ManageIntent.OnSearchCloseButtonClick) },
        onSelectCategoryChip = { index -> onIntent(ManageIntent.OnSelectCategoryChip(index)) }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun ManageScreen(
    modifier: Modifier = Modifier,
    query: String,
    ingredientItems: List<Ingredient>,
    selectedCategoryIndex: Int,
    onClickTopAppBarNavigation: () -> Unit,
    onClickTopAppBarAction: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSearchCloseButtonClick: () -> Unit,
    onSelectCategoryChip: (Int) -> Unit,
) {
    IngredientFarmingTopAppBar(
        title = "관리",
        type = AppBarType.All,
        onClickNavigation = { onClickTopAppBarNavigation() },
        actionIcon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )
        },
        onClickAction = { onClickTopAppBarAction() }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            IngredientFarmingSearchBar(
                query = query,
                onQueryChange = { q -> onSearchQueryChange(q) },
                onCloseClick = { onSearchCloseButtonClick() }
            )

            CategoryFilterChipGroup(
                modifier = modifier,
                selectedCategoryIndex = selectedCategoryIndex,
                onSelectCategoryChip = { index -> onSelectCategoryChip(index) }
            )

            LazyColumn {
                items(ingredientItems) { ingredient ->
                    IngredientCard(
                        modifier = modifier,
                        item = ingredient
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryFilterChipGroup(
    modifier: Modifier = Modifier,
    selectedCategoryIndex: Int,
    onSelectCategoryChip: (Int) -> Unit
) {
    LazyRow(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                modifier = modifier,
                label = { Text("전체") },
                leadingIcon = if (selectedCategoryIndex == 0) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
                onClick = { onSelectCategoryChip(0) },
                selected = selectedCategoryIndex == 0
            )
        }

        items(IngredientCategory.entries) { category ->
            FilterChip(
                modifier = modifier,
                label = { Text(category.n) },
                leadingIcon = if (selectedCategoryIndex - 1 == category.ordinal) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Done icon",
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
                onClick = { onSelectCategoryChip(category.ordinal + 1) },
                selected = selectedCategoryIndex - 1 == category.ordinal
            )
        }
    }
}

@Preview
@Composable
private fun ManageScreenPreview() {
    ManageScreen(
        query = "",
        ingredientItems = listOf(
            Ingredient(
                id = 1,
                name = "감자",
                count = 2,
                expirationDate = LocalDate.now(),
                category = IngredientCategory.VEGETABLE,
                store = IngredientStore.REFRIGERATED
            ),
            Ingredient(
                id = 2,
                name = "당근",
                count = 3,
                expirationDate = LocalDate.now(),
                category = IngredientCategory.VEGETABLE,
                store = IngredientStore.REFRIGERATED
            )
        ),
        selectedCategoryIndex = 1,
        onClickTopAppBarNavigation = {},
        onClickTopAppBarAction = {},
        onSearchQueryChange = {},
        onSearchCloseButtonClick = {},
        onSelectCategoryChip = {}
    )
}
