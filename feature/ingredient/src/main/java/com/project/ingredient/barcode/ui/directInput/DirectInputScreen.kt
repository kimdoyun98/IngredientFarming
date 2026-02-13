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
        ) {
            Box(
                modifier = modifier
                    .weight(1f)
            ) {
                IngredientInputContent(
                    modifier = modifier,
                    name = state().name,
                    count = state().count,
                    expirationDate = state().expirationDate,
                    changeNameValue = { onIntent(DirectInputIntent.NameInputChange(it)) },
                    changeCountValue = { onIntent(DirectInputIntent.CountInputChange(it)) },
                    changeExpirationDateValue = {
                        onIntent(
                            DirectInputIntent.ExpirationDateInputChange(
                                it
                            )
                        )
                    },
                    clickStoreFilterChip = { selected ->
                        onIntent(
                            DirectInputIntent.StoreFilterChipSelect(
                                selected
                            )
                        )
                    },
                    clickCategoryFilterChip = { selected ->
                        onIntent(
                            DirectInputIntent.CategoryFilterChipSelect(
                                selected
                            )
                        )
                    },
                    storeChipSelectIndex = state().storeSelected,
                    categoryChipSelectIndex = state().categorySelected,
                )
            }

            IngredientFarmingWideButton(
                onClick = { onIntent(DirectInputIntent.NextButtonClick) },
                enabled = state().isEnabled()
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
        state = { DirectInputState() },
        onIntent = {}
    )
}
