package com.project.ingredient.barcode.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.designsystem.compose.LocarmDateTextField
import com.project.designsystem.compose.LocarmNumberTextField
import com.project.designsystem.compose.LocarmTextField
import com.project.ingredient.R
import com.project.ui.FilterChipGroup
import com.project.ui.util.rememberIngredientCategoryTitles
import com.project.ui.util.rememberIngredientStoreTitles

@Composable
internal fun IngredientInputContent(
    modifier: Modifier = Modifier,
    name: String,
    count: String,
    expirationDate: String,
    changeNameValue: (String) -> Unit,
    changeCountValue: (String) -> Unit,
    changeExpirationDateValue: (String) -> Unit,
    clickStoreFilterChip: (Int) -> Unit,
    clickCategoryFilterChip: (Int) -> Unit,
    storeChipSelectIndex: Int?,
    categoryChipSelectIndex: Int?,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        LocarmTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = name,
            label = stringResource(R.string.name),
            onValueChange = { changeNameValue(it) }
        )

        LocarmNumberTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = count,
            label = stringResource(R.string.count),
            onValueChange = { changeCountValue(it) },
        )

        LocarmDateTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = expirationDate,
            label = stringResource(R.string.expiration),
            placeholder = stringResource(R.string.expiration_text_field_placeholder),
            onValueChange = { changeExpirationDateValue(it) },
        )

        FilterChipGroup(
            groupName = stringResource(R.string.store),
            groupList = rememberIngredientStoreTitles(),
            onClick = { selected -> clickStoreFilterChip(selected) },
            selectedChipIndex = storeChipSelectIndex ?: -1,
        )

        FilterChipGroup(
            groupName = stringResource(R.string.category),
            groupList = rememberIngredientCategoryTitles(),
            onClick = { selected -> clickCategoryFilterChip(selected) },
            selectedChipIndex = categoryChipSelectIndex ?: -1,
        )
    }
}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
private fun InputContentPreview() {
    IngredientInputContent(
        name = "테스트",
        count = "3",
        expirationDate = "2026-01-01",
        changeNameValue = {},
        changeCountValue = {},
        changeExpirationDateValue = {},
        clickStoreFilterChip = {},
        clickCategoryFilterChip = {},
        storeChipSelectIndex = 1,
        categoryChipSelectIndex = 3,
    )
}
