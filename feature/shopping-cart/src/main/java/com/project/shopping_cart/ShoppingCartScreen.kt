package com.project.shopping_cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.designsystem.theme.LightGreen
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
        saveSuccessItemState = shoppingCartState.saveSuccessItemState,
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
        onSaveSuccessItemsButtonClick = { onIntent(ShoppingCartIntent.OnSaveCartItemsButtonClick) },
    )
}

@Composable
internal fun ShoppingCartScreen(
    modifier: Modifier = Modifier,
    cartList: ImmutableList<ShoppingCart>,
    addState: Boolean,
    saveSuccessItemState: Boolean,
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
    onItemDeleteClick: (Int) -> Unit,
    onSaveSuccessItemsButtonClick: () -> Unit,
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
                cartCount = cartList.size,
                buyCount = cartList.count { it.success }
            )

            if (!addState) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    if (saveSuccessItemState) {
                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = LightGreen),
                            shape = RoundedCornerShape(8.dp),
                            onClick = onSaveSuccessItemsButtonClick,
                        ) {
                            Text(
                                text = stringResource(R.string.save_success_items_button_text),
                                color = DarkGray
                            )
                        }
                    }

                    TextButton(
                        onClick = onItemAddContentShowButtonClick
                    ) {
                        Text(stringResource(R.string.add))
                    }
                }
            }

            if (addState) {
                ShoppingItemAddContent(
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
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(
                    items = cartList,
                    key = { idx, item -> item.name }
                ) { index, item ->
                    ShoppingCartItem(
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
            ),
            saveSuccessItemState = true
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
