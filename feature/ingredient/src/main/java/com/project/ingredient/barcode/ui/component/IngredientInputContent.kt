package com.project.ingredient.barcode.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.ingredient.R
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore
import com.project.ui.FilterChipGroup

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
        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            label = { Text(text = stringResource(R.string.name)) },
            value = name,
            onValueChange = { changeNameValue(it) },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
            ),
        )

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            label = { Text(text = stringResource(R.string.count)) },
            value = count,
            onValueChange = { changeCountValue(it) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
        )

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            label = { Text(text = stringResource(R.string.expiration)) },
            placeholder = { Text(text = stringResource(R.string.expiration_text_field_placeholder)) },
            value = TextFieldValue(
                text = expirationDate,
                selection = TextRange(expirationDate.length)
            ),
            onValueChange = { changeExpirationDateValue(it.text) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
        )

        FilterChipGroup(
            modifier = modifier,
            groupName = stringResource(R.string.store),
            groupList = IngredientStore.entries.map { it.n },
            onClick = { selected -> clickStoreFilterChip(selected) },
            selectedChipIndex = storeChipSelectIndex ?: -1,
        )

        FilterChipGroup(
            modifier = modifier,
            groupName = stringResource(R.string.category),
            groupList = IngredientCategory.entries.map { it.n },
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
