package com.project.shopping_cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.model.cart.ShoppingCart
import com.project.model.ingredient.IngredientCategory
import com.project.shopping_cart.component.ShoppingCartItem
import com.project.shopping_cart.component.ShoppingItemAddContent
import com.project.shopping_cart.component.ShoppingProgress
import com.project.shopping_cart.contract.ShoppingCartIntent
import com.project.shopping_cart.contract.ShoppingCartState
import com.project.ui.AppBarType
import com.project.ui.IngredientFarmingTopAppBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun ShoppingCartScreen(
    modifier: Modifier = Modifier,
    shoppingCartState: ShoppingCartState,
    onIntent: (ShoppingCartIntent) -> Unit,
) {
    ShoppingCartScreen(
        modifier = modifier,
        cartList = shoppingCartState.cartList,
        addState = shoppingCartState.addState,
        addItemNameQuery = shoppingCartState.addItemNameQuery,
        addItemCount = shoppingCartState.addItemCount,
        addItemCategorySelected = shoppingCartState.addItemCategorySelected,
        onTopAppBarNavigationButtonClick = { onIntent(ShoppingCartIntent.OnTopAppBarNavigationButtonClick) },
        onItemAddContentShowButtonClick = { onIntent(ShoppingCartIntent.OnItemAddContentShowButtonClick) },
        onAddItemNameChanged = { onIntent(ShoppingCartIntent.OnAddItemNameChanged(it)) },
        onAddItemCountChanged = { onIntent(ShoppingCartIntent.OnAddItemCountChanged(it)) },
        onAddItemCategoryChanged = { onIntent(ShoppingCartIntent.OnAddItemCategoryChanged(it)) },
        onItemAddCancelButtonClick = { onIntent(ShoppingCartIntent.OnItemAddCancelButtonClick) },
        onItemAddButtonClick = { onIntent(ShoppingCartIntent.OnItemAddButtonClick) },
        onItemCheckBoxClick = { onIntent(ShoppingCartIntent.OnItemCheckBoxClick(it)) },
        onItemDeleteClick = { onIntent(ShoppingCartIntent.OnItemDeleteClick(it)) },
    )
}

@Composable
internal fun ShoppingCartScreen(
    modifier: Modifier = Modifier,
    cartList: ImmutableList<ShoppingCart>,
    addState: Boolean,
    addItemNameQuery: String,
    addItemCount: String,
    addItemCategorySelected: Int,
    onTopAppBarNavigationButtonClick: () -> Unit,
    onItemAddContentShowButtonClick: () -> Unit,
    onAddItemNameChanged: (String) -> Unit,
    onAddItemCountChanged: (String) -> Unit,
    onAddItemCategoryChanged: (Int) -> Unit,
    onItemAddCancelButtonClick: () -> Unit,
    onItemAddButtonClick: () -> Unit,
    onItemCheckBoxClick: (Int) -> Unit,
    onItemDeleteClick: (Int) -> Unit
) {
    IngredientFarmingTopAppBar(
        modifier = modifier,
        title = stringResource(R.string.shopping_cart_screen_title),
        type = AppBarType.Navigation,
        onClickNavigation = onTopAppBarNavigationButtonClick,
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ShoppingProgress(
                modifier = modifier,
                cartCount = cartList.size,
                buyCount = cartList.count { it.success }
            )

            if (!addState) {
                Box(
                    modifier = modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    TextButton(
                        onClick = onItemAddContentShowButtonClick
                    ) {
                        Text(stringResource(R.string.add))
                    }
                }
            }

            if (addState) {
                ShoppingItemAddContent(
                    modifier = modifier,
                    addItemNameQuery = addItemNameQuery,
                    addItemCount = addItemCount,
                    addItemCategorySelected = addItemCategorySelected,
                    onQueryChange = onAddItemNameChanged,
                    onItemCountChange = onAddItemCountChanged,
                    onCategorySelect = onAddItemCategoryChanged,
                    onCancelButtonClick = onItemAddCancelButtonClick,
                    onAddButtonClick = onItemAddButtonClick,
                )
            }

            LazyColumn(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(
                    items = cartList,
                    key = { idx, item -> item.name }
                ) { index, item ->
                    ShoppingCartItem(
                        modifier = modifier,
                        name = item.name,
                        count = item.count,
                        category = item.category,
                        success = item.success,
                        onClick = { onItemCheckBoxClick(index) },
                        onDeleteClick = { onItemDeleteClick(index) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ShoppingCartScreenPreview() {
    ShoppingCartScreen(
        shoppingCartState = ShoppingCartState(
            cartList = persistentListOf(
                ShoppingCart(
                    name = "사과",
                    count = 5,
                    category = IngredientCategory.FRUIT,
                    success = false
                ),

                ShoppingCart(
                    name = "삼겹살",
                    count = 1,
                    category = IngredientCategory.MEAT,
                    success = true
                )
            )
        ),
        onIntent = {}
    )
}

@Preview
@Composable
private fun ShoppingCartAddScreenPreview() {
    ShoppingCartScreen(
        shoppingCartState = ShoppingCartState(
            addState = true,
            cartList = persistentListOf(
                ShoppingCart(
                    name = "사과",
                    count = 5,
                    category = IngredientCategory.FRUIT,
                    success = false
                ),

                ShoppingCart(
                    name = "삼겹살",
                    count = 1,
                    category = IngredientCategory.MEAT,
                    success = true
                )
            ),
            addItemNameQuery = "라면",
            addItemCount = "5",
            addItemCategorySelected = 8
        ),
        onIntent = {}
    )
}
