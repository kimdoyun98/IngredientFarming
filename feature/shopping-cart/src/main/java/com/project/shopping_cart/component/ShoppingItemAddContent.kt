package com.project.shopping_cart.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.project.designsystem.compose.IngredientFarmingButton
import com.project.designsystem.theme.Purple500
import com.project.shopping_cart.R
import com.project.ui.FilterChipGroup
import com.project.ui.modifier.shadowLayout
import com.project.ui.util.rememberIngredientCategoryTitles

@Composable
internal fun ShoppingItemAddContent(
    modifier: Modifier = Modifier,
    addItemNameQuery: String,
    addItemCount: String,
    addItemCategorySelected: Int,
    onQueryChange: (String) -> Unit,
    onCancelButtonClick: () -> Unit,
    onAddButtonClick: () -> Unit,
    onCategorySelect: (Int) -> Unit,
    onItemCountChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .shadowLayout()
            .padding(16.dp)
    ) {
        ItemAddOutlinedTextField(
            value = addItemNameQuery,
            onValueChange = onQueryChange,
            placeholder = { Text(stringResource(R.string.shopping_cart_item_query_placeholder)) },
        )

        ItemAddOutlinedTextField(
            value = addItemCount,
            onValueChange = onItemCountChange,
            placeholder = { Text(stringResource(R.string.shopping_cart_item_count_query_placeholder)) },
            keyboardType = KeyboardType.Number,
        )

        FilterChipGroup(
            modifier = Modifier
                .padding(4.dp),
            groupList = rememberIngredientCategoryTitles(),
            onClick = { idx -> onCategorySelect(idx) },
            selectedChipIndex = addItemCategorySelected
        )

        Row {
            IngredientFarmingButton(
                modifier = Modifier
                    .weight(1f),
                onClick = onCancelButtonClick,
                background = LightGray
            ) {
                Text(
                    stringResource(R.string.cancel),
                    color = Black
                )
            }

            IngredientFarmingButton(
                modifier = Modifier
                    .weight(1f),
                onClick = onAddButtonClick,
                background = Purple500
            ) { Text(stringResource(R.string.add)) }
        }
    }
}
