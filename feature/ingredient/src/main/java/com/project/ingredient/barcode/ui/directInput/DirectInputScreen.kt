package com.project.ingredient.barcode.ui.directInput

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
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
import com.project.designsystem.compose.IngredientFarmingWideButton
import com.project.ingredient.R
import com.project.ingredient.barcode.contract.directInput.DirectInputIntent
import com.project.ingredient.barcode.contract.directInput.DirectInputState
import com.project.model.barcode.IngredientCategory
import com.project.model.barcode.IngredientStore
import com.project.ui.AppBarType
import com.project.ui.IngredientFarmingTopAppBar

@Composable
internal fun DirectInputScreen(
    modifier: Modifier = Modifier,
    state: () -> DirectInputState,
    onIntent: (DirectInputIntent) -> Unit,
) {
    IngredientFarmingTopAppBar(
        title = "직접 입력",
        type = AppBarType.Navigation,
        onClickNavigation = { onIntent(DirectInputIntent.TopAppBarNavigationClick) },
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                modifier = modifier
                    .fillMaxWidth(),
                label = { Text(text = stringResource(R.string.name)) },
                value = state().name,
                onValueChange = { onIntent(DirectInputIntent.NameInputChange(it)) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                ),
            )

            OutlinedTextField(
                modifier = modifier
                    .fillMaxWidth(),
                label = { Text(text = stringResource(R.string.count)) },
                value = "${state().count}",
                onValueChange = { onIntent(DirectInputIntent.CountInputChange(it)) },
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
                    text = state().expirationDate,
                    selection = TextRange(state().expirationDate.length)
                ),
                onValueChange = { onIntent(DirectInputIntent.ExpirationDateInputChange(it.text)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
            )

            FilterChipGroup(
                modifier = modifier,
                groupName = stringResource(R.string.store),
                groupList = IngredientStore.entries.map { it.n },
                onClick = { selected -> onIntent(DirectInputIntent.StoreFilterChipSelect(selected)) },
                selectedChipIndex = state().storeSelected ?: -1,
            )

            FilterChipGroup(
                modifier = modifier,
                groupName = stringResource(R.string.category),
                groupList = IngredientCategory.entries.map { it.n },
                onClick = { selected -> onIntent(DirectInputIntent.CategoryFilterChipSelect(selected)) },
                selectedChipIndex = state().categorySelected ?: -1,
            )

            IngredientFarmingWideButton(
                onClick = { onIntent(DirectInputIntent.NextButtonClick) },
                enabled = state().isEnabled()
            ) {
                Text(text = stringResource(R.string.next))
            }
        }
    }
}

@Composable
private fun FilterChipGroup(
    modifier: Modifier,
    groupName: String,
    groupList: List<String>, //TODO List로 인해 Recomposition 발생 - ImmutableList 사용
    onClick: (Int) -> Unit,
    selectedChipIndex: Int,
) {
    Column {
        Text(text = groupName)

        FlowRow(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            groupList.forEachIndexed { idx, name ->
                FilterChip(
                    onClick = { onClick(idx) },
                    label = { Text(text = name) },
                    selected = idx == selectedChipIndex,
                    leadingIcon = if (idx == selectedChipIndex) {
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
                )
            }
        }
    }
}

@Preview
@Composable
private fun DirectInputScreenPreview() {
    DirectInputScreen(
        state = { DirectInputState() },
        onIntent = {}
    )
}
