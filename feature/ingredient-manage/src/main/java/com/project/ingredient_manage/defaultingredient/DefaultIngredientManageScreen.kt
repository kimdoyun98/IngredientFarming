package com.project.ingredient_manage.defaultingredient

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.project.ingredient_manage.R
import com.project.ingredient_manage.defaultingredient.component.DefaultIngredientItem
import com.project.ingredient_manage.defaultingredient.component.UpdateIngredientDialog
import com.project.ingredient_manage.defaultingredient.contract.DefaultIngredientIntent
import com.project.ingredient_manage.defaultingredient.contract.DefaultIngredientState
import com.project.ingredient_manage.defaultingredient.util.UpdateDefaultIngredientState
import com.project.model.ingredient.DefaultIngredient
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore
import com.project.ui.AppBarType
import com.project.ui.IngredientFarmingCenterLoading
import com.project.ui.IngredientFarmingSearchBar
import com.project.ui.IngredientFarmingTopAppBar
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun DefaultIngredientManageScreen(
    state: DefaultIngredientState,
    ingredients: LazyPagingItems<DefaultIngredient>,
    onIntent: (DefaultIngredientIntent) -> Unit
) {
    DefaultIngredientManageScreen(
        query = state.query,
        selectedCategory = state.selectedCategory,
        ingredients = ingredients,
        showDialog = state.showDialog,
        dialogIngredientName = state.selectedIngredient?.name ?: "",
        selectedDialogCategory = state.selectedDialogCategory,
        selectedDialogStore = state.selectedDialogStore,
        updateState = state.updateState,
        onClickTopAppBarNavigation = { onIntent(DefaultIngredientIntent.OnTopAppBarNavigationClick) },
        onSearchQueryChange = { query -> onIntent(DefaultIngredientIntent.SearchQueryChange(query)) },
        onSearchCloseButtonClick = { onIntent(DefaultIngredientIntent.OnSearchCloseButtonClick) },
        onSelectCategoryChip = { category ->
            onIntent(
                DefaultIngredientIntent.SelectCategoryChip(
                    category
                )
            )
        },
        onItemClick = { ingredient ->
            onIntent(
                DefaultIngredientIntent.OnDefaultIngredientItemClick(
                    ingredient
                )
            )
        },
        showDialogStateChange = { state ->
            onIntent(
                DefaultIngredientIntent.ShowDialogStateChange(
                    state
                )
            )
        },
        onDialogCategorySelect = { category ->
            onIntent(
                DefaultIngredientIntent.OnDialogCategorySelect(
                    category
                )
            )
        },
        onDialogStoreSelect = { store -> onIntent(DefaultIngredientIntent.OnDialogStoreSelect(store)) },
        onDialogDismissButtonClick = { onIntent(DefaultIngredientIntent.OnDialogDismissButtonClick) },
        onDialogSaveButtonClick = { onIntent(DefaultIngredientIntent.OnDialogSaveButtonClick) },
    )
}

@Composable
internal fun DefaultIngredientManageScreen(
    modifier: Modifier = Modifier,
    query: String,
    selectedCategory: IngredientCategory?,
    showDialog: Boolean,
    dialogIngredientName: String,
    selectedDialogCategory: IngredientCategory?,
    selectedDialogStore: IngredientStore?,
    ingredients: LazyPagingItems<DefaultIngredient>,
    updateState: UpdateDefaultIngredientState,
    onClickTopAppBarNavigation: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSearchCloseButtonClick: () -> Unit,
    onSelectCategoryChip: (IngredientCategory?) -> Unit,
    onItemClick: (DefaultIngredient) -> Unit,
    showDialogStateChange: (Boolean) -> Unit,
    onDialogCategorySelect: (IngredientCategory) -> Unit,
    onDialogStoreSelect: (IngredientStore) -> Unit,
    onDialogDismissButtonClick: () -> Unit,
    onDialogSaveButtonClick: () -> Unit,
) {
    IngredientFarmingTopAppBar(
        title = stringResource(R.string.default_ingredients_manage_top_app_bar_title),
        type = AppBarType.Navigation,
        onClickNavigation = onClickTopAppBarNavigation,
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                IngredientFarmingSearchBar(
                    query = query,
                    onQueryChange = { q -> onSearchQueryChange(q) },
                    onCloseClick = onSearchCloseButtonClick
                )

                CategoryFilterChipGroup(
                    selectedCategory = selectedCategory,
                    onSelectCategoryChip = { category -> onSelectCategoryChip(category) }
                )

                LazyColumn(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    items(
                        count = ingredients.itemCount,
                        key = { ingredients[it]?.id ?: -1 }
                    ) {
                        val defaultIngredient = ingredients[it]!!

                        DefaultIngredientItem(
                            name = defaultIngredient.name,
                            category = defaultIngredient.category,
                            store = defaultIngredient.store,
                            isComplete = defaultIngredient.isComplete,
                            onClick = { onItemClick(defaultIngredient) }
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }

    if (showDialog) {
        UpdateIngredientDialog(
            name = dialogIngredientName,
            selectedCategory = selectedDialogCategory,
            selectedStore = selectedDialogStore,
            showDialogStateChange = { state -> showDialogStateChange(state) },
            onClickCategory = { category -> onDialogCategorySelect(category) },
            onClickStore = { store -> onDialogStoreSelect(store) },
            onClickDismiss = onDialogDismissButtonClick,
            onClickSave = onDialogSaveButtonClick
        )

        if(updateState is UpdateDefaultIngredientState.Loading){
            IngredientFarmingCenterLoading()
        }
    }

    BackHandler {
        onClickTopAppBarNavigation()
    }
}

@Composable
private fun CategoryFilterChipGroup(
    modifier: Modifier = Modifier,
    selectedCategory: IngredientCategory?,
    onSelectCategoryChip: (IngredientCategory?) -> Unit
) {
    LazyRow(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                label = { Text(stringResource(R.string.total)) },
                leadingIcon = if (selectedCategory == null) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = stringResource(R.string.filter_chip_check_icon_description),
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
                onClick = { onSelectCategoryChip(null) },
                selected = selectedCategory == null
            )
        }

        items(IngredientCategory.entries) { category ->
            FilterChip(
                label = { Text(category.title) },
                leadingIcon = if (selectedCategory == category) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = stringResource(R.string.filter_chip_check_icon_description),
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                },
                onClick = { onSelectCategoryChip(category) },
                selected = selectedCategory == category
            )
        }
    }
}

@Preview
@Composable
private fun DefaultIngredientManageScreenPreview() {
    val fakeList = listOf(
        DefaultIngredient(
            id = 0,
            name = "사과",
            category = IngredientCategory.FRUIT,
            isComplete = true
        ),
        DefaultIngredient(
            id = 2,
            name = "대파",
            category = IngredientCategory.VEGETABLE,
            isComplete = false
        ),
    )
    val pagingItems = flowOf(PagingData.from(fakeList)).collectAsLazyPagingItems()

    DefaultIngredientManageScreen(
        state = DefaultIngredientState(),
        ingredients = pagingItems,
        onIntent = {}
    )
}
