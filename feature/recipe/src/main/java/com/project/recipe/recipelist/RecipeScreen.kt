package com.project.recipe.recipelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.project.model.recipe.RecipeCategory
import com.project.recipe.R
import com.project.recipe.recipelist.component.RecipeCardItem
import com.project.recipe.recipelist.contract.RecipeIntent
import com.project.recipe.recipelist.contract.RecipeState
import com.project.recipe.recipelist.model.IngredientsAvailable
import com.project.recipe.recipelist.model.RecipeListItemUiModel
import com.project.ui.AppBarType
import com.project.ui.IngredientFarmingCenterLoading
import com.project.ui.IngredientFarmingSearchBar
import com.project.ui.IngredientFarmingTopAppBar
import kotlinx.coroutines.flow.flowOf

@Composable
internal fun RecipeScreen(
    state: RecipeState,
    recipes: LazyPagingItems<RecipeListItemUiModel>,
    onIntent: (RecipeIntent) -> Unit
) {
    RecipeScreen(
        query = state.query,
        selectedCategory = state.selectedCategory,
        recipes = recipes,
        onTopAppBarNavigationClick = { onIntent(RecipeIntent.OnTopAppBarNavigationClick) },
        onTopAppBarActionClick = { onIntent(RecipeIntent.OnTopAppBarActionClick) },
        onSearchRecipeQueryChange = { query -> onIntent(RecipeIntent.SearchRecipeQueryChange(query)) },
        onSearchRecipeQueryReset = { onIntent(RecipeIntent.SearchRecipeQueryReset) },
        onSelectCategoryChip = { category -> onIntent(RecipeIntent.SelectCategoryChip(category)) },
        onRecipeItemClick = { id -> onIntent(RecipeIntent.RecipeItemClick(id)) }
    )
}

@Composable
internal fun RecipeScreen(
    modifier: Modifier = Modifier,
    query: String,
    selectedCategory: RecipeCategory?,
    recipes: LazyPagingItems<RecipeListItemUiModel>,
    onTopAppBarNavigationClick: () -> Unit,
    onTopAppBarActionClick: () -> Unit,
    onSearchRecipeQueryChange: (String) -> Unit,
    onSearchRecipeQueryReset: () -> Unit,
    onSelectCategoryChip: (RecipeCategory?) -> Unit,
    onRecipeItemClick: (Int) -> Unit,
) {
    IngredientFarmingTopAppBar(
        type = AppBarType.All,
        title = stringResource(R.string.recipe_top_app_bar_title),
        actionIcon = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.recipe_top_app_bar_action_description),
            )
        },
        onClickNavigation = onTopAppBarNavigationClick,
        onClickAction = onTopAppBarActionClick,
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 8.dp),
        ) {
            IngredientFarmingSearchBar(
                query = query,
                onQueryChange = { query -> onSearchRecipeQueryChange(query) },
                onCloseClick = onSearchRecipeQueryReset,
                placeholder = "레시피 검색"
            )

            CategoryFilterChipGroup(
                selectedCategory = selectedCategory,
                onSelectCategoryChip = { category -> onSelectCategoryChip(category) }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    count = recipes.itemCount,
                    key = { recipes[it]?.id ?: -1 }
                ) { idx ->
                    val recipe = recipes[idx]
                    if (recipe != null) {
                        RecipeCardItem(
                            imagePath = recipe.image,
                            name = recipe.name,
                            category = recipe.category,
                            time = recipe.minute,
                            people = recipe.people,
                            totalIngredient = recipe.ingredientsAvailable.total,
                            holdIngredient = recipe.ingredientsAvailable.isAvailableCount,
                            onClick = { onRecipeItemClick(recipe.id) }
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                recipes.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { IngredientFarmingCenterLoading() }
                        }

                        loadState.append is LoadState.Loading -> {
                            item { IngredientFarmingCenterLoading() }
                        }

                        loadState.append is LoadState.Error -> {
                            item { Text("에러 발생") }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryFilterChipGroup(
    modifier: Modifier = Modifier,
    selectedCategory: RecipeCategory?,
    onSelectCategoryChip: (RecipeCategory?) -> Unit,
) {
    LazyRow(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                label = { Text(stringResource(R.string.category_total)) },
                onClick = { onSelectCategoryChip(null) },
                selected = selectedCategory == null
            )
        }

        items(RecipeCategory.entries) { category ->
            FilterChip(
                label = { Text(category.title) },
                onClick = { onSelectCategoryChip(category) },
                selected = selectedCategory == category
            )
        }
    }
}

@Preview
@Composable
private fun RecipeScreenPreview() {
    val fakeList = listOf(
        RecipeListItemUiModel(
            id = 0,
            name = "김치찌개",
            ingredientsAvailable = IngredientsAvailable(5, 3)
        ),
        RecipeListItemUiModel(
            id = 2,
            name = "된장찌개",
            ingredientsAvailable = IngredientsAvailable(5, 1)
        ),
    )
    val pagingItems = flowOf(PagingData.from(fakeList)).collectAsLazyPagingItems()

    RecipeScreen(
        state = RecipeState(),
        recipes = pagingItems,
        onIntent = {}
    )
}
