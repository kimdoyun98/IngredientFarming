package com.project.ingredient_manage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.designsystem.compose.IngredientFarmingWideButton
import com.project.ingredient_manage.contract.ManageIntent
import com.project.ingredient_manage.contract.ManageState
import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore
import com.project.ui.AppBarType
import com.project.ui.IconIngredientCard
import com.project.ui.IngredientFarmingSearchBar
import com.project.ui.IngredientFarmingTopAppBar
import com.project.ui.modifier.singleClickable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import java.time.LocalDate

@Composable
internal fun ManageScreen(
    modifier: Modifier = Modifier,
    manageState: ManageState,
    onIntent: (ManageIntent) -> Unit,
) {
    ManageScreen(
        modifier = modifier,
        query = manageState.query,
        ingredientItems = manageState.ingredientItems,
        selectedCategoryIndex = manageState.selectedCategoryIndex,
        deleteOptionsState = manageState.deleteOptionsState,
        allSelectedState = manageState.allSelectedState,
        selectedItems = manageState.selectedItems,
        onClickTopAppBarNavigation = { onIntent(ManageIntent.OnClickTopAppBarNavigation) },
        onClickTopAppBarAction = { onIntent(ManageIntent.OnClickTopAppBarAction) },
        onSearchQueryChange = { q -> onIntent(ManageIntent.OnSearchQueryChange(q)) },
        onSearchCloseButtonClick = { onIntent(ManageIntent.OnSearchCloseButtonClick) },
        onSelectCategoryChip = { index -> onIntent(ManageIntent.OnSelectCategoryChip(index)) },
        onItemClick = { id -> onIntent(ManageIntent.OnClickItem(id)) },
        onItemLongClick = { id -> onIntent(ManageIntent.OnLongClickItem(id)) },
        onAllSelectClick = { onIntent(ManageIntent.OnClickAllSelectRadioButton) },
        onCancelClick = { onIntent(ManageIntent.OnClickDeleteOptionsCancel) },
        onDeleteClick = { onIntent(ManageIntent.OnDeleteButtonClick) }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun ManageScreen(
    modifier: Modifier = Modifier,
    query: String,
    ingredientItems: ImmutableList<Ingredient>,
    selectedCategoryIndex: Int,
    deleteOptionsState: Boolean,
    allSelectedState: Boolean,
    selectedItems: ImmutableMap<Int, Boolean>,
    onClickTopAppBarNavigation: () -> Unit,
    onClickTopAppBarAction: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onSearchCloseButtonClick: () -> Unit,
    onSelectCategoryChip: (Int) -> Unit,
    onItemClick: (Int) -> Unit,
    onItemLongClick: (Int) -> Unit,
    onAllSelectClick: () -> Unit,
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    IngredientFarmingTopAppBar(
        title = stringResource(R.string.top_app_bar_title),
        type = AppBarType.All,
        onClickNavigation = { onClickTopAppBarNavigation() },
        actionIcon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.top_app_bar_action_icon_ingredient_add)
            )
        },
        onClickAction = { onClickTopAppBarAction() }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                IngredientFarmingSearchBar(
                    modifier = modifier,
                    query = query,
                    onQueryChange = { q -> onSearchQueryChange(q) },
                    onCloseClick = { onSearchCloseButtonClick() }
                )

                CategoryFilterChipGroup(
                    modifier = modifier,
                    selectedCategoryIndex = selectedCategoryIndex,
                    onSelectCategoryChip = { index -> onSelectCategoryChip(index) }
                )

                if (deleteOptionsState) {
                    DeleteOptionContent(
                        modifier = modifier,
                        allSelectedState = allSelectedState,
                        onAllSelectClick = onAllSelectClick,
                        onCancelClick = onCancelClick
                    )
                }

                LazyColumn {
                    items(
                        items = ingredientItems,
                        key = { ingredient -> ingredient.id }
                    ) { ingredient ->
                        IconIngredientCard(
                            modifier = modifier,
                            item = ingredient,
                            borderColor =
                                if (deleteOptionsState && selectedItems[ingredient.id] ?: false) Color.Blue
                                else Color.Gray,
                            onClick = { onItemClick(ingredient.id) },
                            onLongClick = { onItemLongClick(ingredient.id) }
                        )
                    }
                }
            }

            if (deleteOptionsState) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {
                    IngredientFarmingWideButton(
                        modifier = modifier,
                        onClick = onDeleteClick
                    ) { Text("재료 소진/폐기") }
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
                label = { Text(stringResource(R.string.total)) },
                leadingIcon = if (selectedCategoryIndex == 0) {
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
                            contentDescription = stringResource(R.string.filter_chip_check_icon_description),
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

@Composable
private fun DeleteOptionContent(
    modifier: Modifier = Modifier,
    allSelectedState: Boolean,
    onAllSelectClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = modifier
                .align(Alignment.CenterStart)
                .singleClickable(onClick = onAllSelectClick),
        ) {
            RadioButton(
                selected = allSelectedState,
                onClick = null
            )
            Text(
                text = "전체 선택",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        TextButton(
            modifier = modifier.align(Alignment.CenterEnd),
            onClick = onCancelClick
        ) {
            Text(
                text = "취소",
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

@Preview
@Composable
private fun ManageScreenPreview() {
    ManageScreen(
        manageState = ManageState(
            ingredientItems = persistentListOf(
                Ingredient(
                    id = 1,
                    name = "감자",
                    count = 2,
                    expirationDate = LocalDate.now().plusDays(1),
                    category = IngredientCategory.VEGETABLE,
                    store = IngredientStore.REFRIGERATED
                ),
                Ingredient(
                    id = 2,
                    name = "돼지고기",
                    count = 3,
                    expirationDate = LocalDate.parse("2026-03-02"),
                    category = IngredientCategory.MEAT,
                    store = IngredientStore.REFRIGERATED
                )
            ),
            selectedCategoryIndex = 1,
            deleteOptionsState = false,
            allSelectedState = false,
        ),
        onIntent = {},
    )
}

@Preview
@Composable
private fun ManageScreenDeleteOptionsPreview() {
    ManageScreen(
        manageState = ManageState(
            ingredientItems = persistentListOf(
                Ingredient(
                    id = 1,
                    name = "감자",
                    count = 2,
                    expirationDate = LocalDate.now().plusDays(1),
                    category = IngredientCategory.VEGETABLE,
                    store = IngredientStore.REFRIGERATED
                ),
                Ingredient(
                    id = 2,
                    name = "돼지고기",
                    count = 3,
                    expirationDate = LocalDate.parse("2026-03-02"),
                    category = IngredientCategory.MEAT,
                    store = IngredientStore.REFRIGERATED
                )
            ),
            selectedCategoryIndex = 1,
            deleteOptionsState = true,
            allSelectedState = true,
            selectedItems = persistentMapOf<Int, Boolean>(1 to true, 2 to true)
        ),
        onIntent = {},
    )
}
