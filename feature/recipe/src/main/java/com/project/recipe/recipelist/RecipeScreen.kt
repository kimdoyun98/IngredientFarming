package com.project.recipe.recipelist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.model.recipe.IngredientUnit
import com.project.model.recipe.RecipeCategory
import com.project.model.recipe.RecipeIngredient
import com.project.model.recipe.RecipeListItem
import com.project.recipe.R
import com.project.recipe.recipelist.component.RecipeCardItem
import com.project.recipe.recipelist.contract.RecipeIntent
import com.project.recipe.recipelist.contract.RecipeState
import com.project.ui.AppBarType
import com.project.ui.IngredientFarmingSearchBar
import com.project.ui.IngredientFarmingTopAppBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun RecipeScreen(
    state: RecipeState,
    onIntent: (RecipeIntent) -> Unit
) {
    RecipeScreen(
        query = state.query,
        selectedCategory = state.selectedCategory,
        recipeList = state.recipeList,
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
    recipeList: ImmutableList<RecipeListItem>,
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
                modifier = Modifier.weight(1f)
            ) {
                items(
                    items = recipeList,
                    key = { it.id }
                ) {
                    RecipeCardItem(
                        imageUri = it.photo,
                        name = it.name,
                        category = it.category,
                        time = it.minute,
                        people = it.people,
                        totalIngredient = it.ingredients.size,
                        holdIngredient = 2,
                        onClick = { onRecipeItemClick(it.id) }
                    )

                    Spacer(modifier = Modifier.height(8.dp))
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
    RecipeScreen(
        state = RecipeState(
            recipeList = persistentListOf(
                RecipeListItem(
                    id = 0,
                    photo = "",
                    name = "비빔밥",
                    category = RecipeCategory.KOREAN_FOOD,
                    minute = 30,
                    people = 2,
                    ingredients = listOf(
                        RecipeIngredient(
                            id = 0,
                            name = "당근",
                            count = 1.0,
                            unit = IngredientUnit.COUNT
                        ),
                        RecipeIngredient(
                            id = 1,
                            name = "계란",
                            count = 1.0,
                            unit = IngredientUnit.COUNT
                        ),
                        RecipeIngredient(
                            id = 2,
                            name = "고추장",
                            count = 1.0,
                            unit = IngredientUnit.TABLESPOON
                        ),
                        RecipeIngredient(
                            id = 3,
                            name = "참기름",
                            count = 1.0,
                            unit = IngredientUnit.TABLESPOON
                        )
                    )
                )
            )
        ),
        onIntent = {}
    )
}
