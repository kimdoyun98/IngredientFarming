package com.project.ingredient.barcode.ui.directInput

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.project.designsystem.compose.IngredientFarmingWideButton
import com.project.ingredient.R
import com.project.ingredient.barcode.contract.directInput.DirectInputIntent
import com.project.ingredient.barcode.contract.directInput.DirectInputState
import com.project.ingredient.barcode.ui.component.IngredientInputContent
import com.project.ui.AppBarType
import com.project.ui.IngredientFarmingTopAppBar

@Composable
internal fun DirectInputScreen(
    modifier: Modifier = Modifier,
    directInputState: DirectInputState,
    onIntent: (DirectInputIntent) -> Unit,
) {
    DirectInputScreen(
        modifier = modifier,
        name = directInputState.name,
        count = directInputState.count,
        expirationDate = directInputState.expirationDate,
        storeChipSelectIndex = directInputState.storeSelected,
        categoryChipSelectIndex = directInputState.categorySelected,
        nextButtonEnabled = directInputState.isEnabled(),
        onClickNavigation = { onIntent(DirectInputIntent.TopAppBarNavigationClick) },
        onChangeNameValue = { name -> onIntent(DirectInputIntent.NameInputChange(name)) },
        onChangeCountValue = { count -> onIntent(DirectInputIntent.CountInputChange(count)) },
        onChangeExpirationDateValue = { date ->
            onIntent(DirectInputIntent.ExpirationDateInputChange(date))
        },
        onClickStoreFilterChip = { selected ->
            onIntent(DirectInputIntent.StoreFilterChipSelect(selected))
        },
        onClickCategoryFilterChip = { selected ->
            onIntent(DirectInputIntent.CategoryFilterChipSelect(selected))
        },
        onClickNextButton = { onIntent(DirectInputIntent.NextButtonClick) },
    )
}

@Composable
internal fun DirectInputScreen(
    modifier: Modifier = Modifier,
    name: String,
    count: String,
    expirationDate: String,
    storeChipSelectIndex: Int?,
    categoryChipSelectIndex: Int?,
    nextButtonEnabled: Boolean,
    onClickNavigation: () -> Unit,
    onChangeNameValue: (String) -> Unit,
    onChangeCountValue: (String) -> Unit,
    onChangeExpirationDateValue: (String) -> Unit,
    onClickStoreFilterChip: (Int) -> Unit,
    onClickCategoryFilterChip: (Int) -> Unit,
    onClickNextButton: () -> Unit,
) {

    IngredientFarmingTopAppBar(
        title = "직접 입력",
        type = AppBarType.Navigation,
        onClickNavigation = onClickNavigation,
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
        ) {
            Box(
                modifier = modifier
                    .weight(1f)
            ) {
                IngredientInputContent(
                    modifier = modifier,
                    name = name,
                    count = count,
                    expirationDate = expirationDate,
                    changeNameValue = onChangeNameValue,
                    changeCountValue = onChangeCountValue,
                    changeExpirationDateValue = onChangeExpirationDateValue,
                    clickStoreFilterChip = onClickStoreFilterChip,
                    clickCategoryFilterChip = onClickCategoryFilterChip,
                    storeChipSelectIndex = storeChipSelectIndex,
                    categoryChipSelectIndex = categoryChipSelectIndex,
                )
            }

            IngredientFarmingWideButton(
                onClick = onClickNextButton,
                enabled = nextButtonEnabled
            ) {
                Text(text = stringResource(R.string.next))
            }
        }
    }
}

@Preview
@Composable
private fun DirectInputScreenPreview() {
    DirectInputScreen(
        directInputState =  DirectInputState() ,
        onIntent = {},
    )
}
