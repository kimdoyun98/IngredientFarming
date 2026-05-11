package com.project.ingredient_manage.defaultingredient.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.project.designsystem.compose.IngredientFarmingButton
import com.project.designsystem.theme.Green
import com.project.designsystem.theme.MoreLightGreen
import com.project.ingredient_manage.R
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore
import com.project.ui.iconbox.SelectCategoryIconBox
import com.project.ui.modifier.singleClickable

@Composable
internal fun UpdateIngredientDialog(
    modifier: Modifier = Modifier,
    name: String,
    selectedCategory: IngredientCategory?,
    selectedStore: IngredientStore?,
    showDialogStateChange: (Boolean) -> Unit,
    onClickCategory: (IngredientCategory) -> Unit,
    onClickStore: (IngredientStore) -> Unit,
    onClickDismiss: () -> Unit,
    onClickSave: () -> Unit,
) {
    Dialog(
        onDismissRequest = { showDialogStateChange(false) },
        properties = DialogProperties(
            dismissOnClickOutside = false
        )
    ) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    text = stringResource(R.string.default_ingredients_dialog_title),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = name,
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray
                )

                HorizontalDivider(
                    thickness = 1.dp,
                    color = LightGray
                )

                Text(
                    text = stringResource(R.string.default_ingredients_dialog_category),
                    style = MaterialTheme.typography.bodySmall,
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IngredientCategory.entries.forEach {
                        SelectCategoryIconBox(
                            modifier = Modifier
                                .singleClickable { onClickCategory(it) },
                            category = it,
                            selected = selectedCategory == it
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(R.string.default_ingredients_dialog_store),
                    style = MaterialTheme.typography.bodySmall,
                )

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IngredientStore.entries.forEach {
                        SelectStoreBox(
                            title = it.title,
                            selected = selectedStore == it,
                            onClick = { onClickStore(it) }
                        )
                    }
                }

                HorizontalDivider(
                    thickness = 1.dp,
                    color = LightGray
                )

                Row {
                    IngredientFarmingButton(
                        modifier = Modifier
                            .height(40.dp)
                            .weight(1f),
                        contentPadding = PaddingValues(vertical = 0.dp),
                        background = LightGray,
                        onClick = onClickDismiss
                    ) {
                        Text(
                            text = stringResource(R.string.default_ingredients_dialog_dismiss_bt_text),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    IngredientFarmingButton(
                        modifier = Modifier
                            .height(40.dp)
                            .weight(1f),
                        contentPadding = PaddingValues(vertical = 0.dp),
                        background = Green,
                        onClick = onClickSave
                    ) {
                        Text(
                            text = stringResource(R.string.default_ingredients_dialog_save_bt_text),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectStoreBox(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(60.dp)
            .height(40.dp)
            .border(
                width = 1.dp,
                color = if (selected) Green else Color.LightGray,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .background(color = if (selected) MoreLightGreen else Color.Transparent)
            .singleClickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 10.sp
        )
    }
}

@Preview
@Composable
private fun UpdateIngredientDialogPreview() {
    UpdateIngredientDialog(
        name = "테스트",
        selectedCategory = IngredientCategory.CONDIMENT,
        selectedStore = IngredientStore.REFRIGERATED,
        showDialogStateChange = {},
        onClickCategory = {},
        onClickStore = {},
        onClickDismiss = {},
        onClickSave = {}
    )
}
