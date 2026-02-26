package com.project.ingredient.barcode.ui.save

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.designsystem.compose.IngredientFarmingWideButton
import com.project.ingredient.R
import com.project.ingredient.barcode.contract.directInput.DirectInputState
import com.project.ingredient.barcode.contract.save.SaveIngredientIntent
import com.project.ingredient.barcode.contract.save.SaveIngredientState
import com.project.ingredient.barcode.ui.component.IngredientInputContent
import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore
import com.project.ui.AddTypeSelectBottomSheetContent
import com.project.ui.AppBarType
import com.project.ui.IngredientCard
import com.project.ui.IngredientFarmingTopAppBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
internal fun SaveIngredientScreen(
    modifier: Modifier = Modifier,
    saveIngredientState: SaveIngredientState,
    onIntent: (SaveIngredientIntent) -> Unit,
    onBottomSheetIntent: (SaveIngredientIntent.BottomSheetIntent) -> Unit
) {
    SaveIngredientScreen(
        modifier = modifier,
        ingredientList = saveIngredientState.ingredientList,
        updateBottomSheetState = saveIngredientState.openUpdateBottomSheetState,
        addBottomSheetState = saveIngredientState.openAddBottomSheetState,
        onPlusButtonClick = { onIntent(SaveIngredientIntent.PlusButtonClick) },
        onUpdateButtonClick = { idx -> onIntent(SaveIngredientIntent.UpdateButtonClick(idx)) },
        onDismissRequestToUpdate = { onIntent(SaveIngredientIntent.UpdateBottomSheetDisMiss) },
        onDismissRequestToAdd = { onIntent(SaveIngredientIntent.AddBottomSheetDisMiss) },
        onSaveButtonClick = { onIntent(SaveIngredientIntent.SaveButtonClick) },
        //bottomSheet
        updateIngredient = saveIngredientState.updateIngredient,
        onBarcodeScannerClick = { onBottomSheetIntent(SaveIngredientIntent.BottomSheetIntent.BarcodeScannerClick) },
        onDirectInputClick = { onBottomSheetIntent(SaveIngredientIntent.BottomSheetIntent.DirectInputClick) },
        changeNameValue = { name ->
            onBottomSheetIntent(SaveIngredientIntent.BottomSheetIntent.NameInputChange(name))
        },
        changeCountValue = { count ->
            onBottomSheetIntent(SaveIngredientIntent.BottomSheetIntent.CountInputChange(count))
        },
        changeExpirationDateValue = { date ->
            onBottomSheetIntent(
                SaveIngredientIntent.BottomSheetIntent.ExpirationDateInputChange(date)
            )
        },
        clickStoreFilterChip = { idx ->
            onBottomSheetIntent(
                SaveIngredientIntent.BottomSheetIntent.StoreFilterChipSelect(idx)
            )
        },
        clickCategoryFilterChip = { idx ->
            onBottomSheetIntent(SaveIngredientIntent.BottomSheetIntent.CategoryFilterChipSelect(idx))
        },
        clickUpdateButton = { onBottomSheetIntent(SaveIngredientIntent.BottomSheetIntent.UpdateButtonClick) }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun SaveIngredientScreen(
    modifier: Modifier = Modifier,
    ingredientList: ImmutableList<Ingredient>,
    updateBottomSheetState: Boolean,
    addBottomSheetState: Boolean,
    onPlusButtonClick: () -> Unit,
    onUpdateButtonClick: (Int) -> Unit,
    onDismissRequestToUpdate: () -> Unit,
    onDismissRequestToAdd: () -> Unit,
    onSaveButtonClick: () -> Unit,
    //bottomSheet
    updateIngredient: DirectInputState,
    onBarcodeScannerClick: () -> Unit,
    onDirectInputClick: () -> Unit,
    changeNameValue: (String) -> Unit,
    changeCountValue: (String) -> Unit,
    changeExpirationDateValue: (String) -> Unit,
    clickStoreFilterChip: (Int) -> Unit,
    clickCategoryFilterChip: (Int) -> Unit,
    clickUpdateButton: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(updateBottomSheetState) {
        if (updateBottomSheetState) {
            delay(100)
            scope.launch { sheetState.expand() }
        }
    }

    IngredientFarmingTopAppBar(
        title = stringResource(R.string.top_app_bar_save_ingredient_screen_title),
        type = AppBarType.Navigation
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Row(modifier = modifier.align(Alignment.End)) {
                TextButton(
                    modifier = modifier,
                    onClick = onPlusButtonClick
                ) {
                    Text(stringResource(R.string.add))
                }
            }

            Box(modifier = modifier.weight(1f)) {
                LazyColumn(
                    modifier = modifier
                ) {
                    items(
                        count = ingredientList.size,
                        key = { idx -> ingredientList[idx].name }
                    ) { idx ->
                        Box {
                            IngredientCard(
                                modifier = modifier,
                                item = ingredientList[idx]
                            )

                            IconButton(
                                modifier = modifier
                                    .align(Alignment.BottomEnd)
                                    .padding(12.dp),
                                onClick = { onUpdateButtonClick(idx) },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    tint = Color.Blue
                                )
                            }
                        }
                    }
                }
            }

            IngredientFarmingWideButton(
                onClick = { onSaveButtonClick() }
            ) {
                Text(stringResource(R.string.save))
            }
        }

        if (updateBottomSheetState) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = onDismissRequestToUpdate
            ) {
                UpdateBottomSheetContent(
                    modifier = modifier,
                    updateIngredient = updateIngredient,
                    onCloseButtonClick = onDismissRequestToUpdate,
                    changeNameValue = changeNameValue,
                    changeCountValue = changeCountValue,
                    changeExpirationDateValue = changeExpirationDateValue,
                    clickStoreFilterChip = clickStoreFilterChip,
                    clickCategoryFilterChip = clickCategoryFilterChip,
                    clickUpdateButton = {
                        clickUpdateButton()
                        scope.launch { sheetState.hide() }
                    }
                )
            }
        }

        if (addBottomSheetState) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = onDismissRequestToAdd
            ) {
                AddTypeSelectBottomSheetContent(
                    modifier = modifier,
                    onBarcodeScannerClick = {
                        onBarcodeScannerClick()
                        scope.launch { sheetState.hide() }
                    },
                    onDirectInputClick = {
                        onDirectInputClick()
                        scope.launch { sheetState.hide() }
                    }
                )
            }
        }
    }
}

@Composable
private fun UpdateBottomSheetContent(
    modifier: Modifier = Modifier,
    updateIngredient: DirectInputState,
    onCloseButtonClick: () -> Unit,
    changeNameValue: (String) -> Unit,
    changeCountValue: (String) -> Unit,
    changeExpirationDateValue: (String) -> Unit,
    clickStoreFilterChip: (Int) -> Unit,
    clickCategoryFilterChip: (Int) -> Unit,
    clickUpdateButton: () -> Unit
) {
    Column {
        Box(
            modifier = modifier.weight(1f)
        ) {
            IconButton(
                modifier = modifier
                    .align(Alignment.TopEnd),
                onClick = onCloseButtonClick
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(R.string.bottom_sheet_close_button)
                )
            }

            IngredientInputContent(
                modifier = modifier,
                name = updateIngredient.name,
                count = updateIngredient.count,
                expirationDate = updateIngredient.expirationDate,
                storeChipSelectIndex = updateIngredient.storeSelected,
                categoryChipSelectIndex = updateIngredient.categorySelected,
                changeNameValue = changeNameValue,
                changeCountValue = changeCountValue,
                changeExpirationDateValue = changeExpirationDateValue,
                clickStoreFilterChip = clickStoreFilterChip,
                clickCategoryFilterChip = clickCategoryFilterChip,
            )
        }

        IngredientFarmingWideButton(
            onClick = clickUpdateButton,
            enabled = updateIngredient.isEnabled(),
        ) {
            Text(text = stringResource(R.string.bottom_sheet_update_button_text))
        }
    }
}

@Preview
@Composable
private fun SaveIngredientScreenPreview() {
    SaveIngredientScreen(
        saveIngredientState =
            SaveIngredientState(
                ingredientList = persistentListOf(
                    Ingredient(
                        name = "요구르트",
                        count = 10,
                        category = IngredientCategory.DAIRY,
                        store = IngredientStore.REFRIGERATED,
                        expirationDate = LocalDate.parse("2026-02-24")
                    ),
                    Ingredient(
                        name = "사과",
                        count = 10,
                        category = IngredientCategory.FRUIT,
                        store = IngredientStore.ROOM_TEMPERATURE,
                        expirationDate = LocalDate.parse("2026-02-24")
                    )
                )
            ),
        onIntent = {},
        onBottomSheetIntent = {}
    )
}
