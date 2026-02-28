package com.project.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.designsystem.theme.Green
import com.project.designsystem.theme.LightPink
import com.project.designsystem.theme.MoreLightGreen
import com.project.designsystem.theme.MoreLightOrange
import com.project.designsystem.theme.Orange
import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore
import com.project.ui.util.daysLeft
import com.project.ui.util.getLuminanceTextColor
import java.time.LocalDate

@Composable
fun IngredientCard(
    modifier: Modifier = Modifier,
    item: Ingredient
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        border = BorderStroke(1.dp, Color.Gray),
    ) {
        IngredientCardContent(
            modifier = modifier,
            item = item,
            isIconCard = false
        )
    }
}

@Composable
fun IconIngredientCard(
    modifier: Modifier = Modifier,
    item: Ingredient,
    borderColor: Color = Color.Gray,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        border = BorderStroke(1.dp, borderColor),
    ) {
        Row(
            modifier = modifier
        ) {
            CategorySmallIconBox(
                modifier = modifier
                    .offset(x = 8.dp, y = 24.dp)
                    .padding(4.dp),
                category = item.category
            )

            IngredientCardContent(
                modifier = modifier,
                item = item,
                isIconCard = true
            )
        }

    }
}

@Composable
private fun IngredientCardContent(
    modifier: Modifier = Modifier,
    item: Ingredient,
    isIconCard: Boolean,
) {
    val leftDays = remember(item.expirationDate) {
        daysLeft(item.expirationDate)
    }

    Column(
        modifier = modifier
            .padding(16.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = modifier.padding(start = 4.dp),
                text = item.name,
                style = MaterialTheme.typography.titleMedium
            )

            SuggestionChip(
                onClick = { },
                label = {
                    Text(
                        text =
                            if (isIconCard) "D-$leftDays"
                            else item.category.n,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                border = null,
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor =
                        if (isIconCard) {
                            when (leftDays) {
                                0L, 1L -> LightPink
                                2L, 3L -> MoreLightOrange
                                else -> MoreLightGreen
                            }
                        } else {
                            Color(item.category.color)
                        },
                    labelColor =
                        if (isIconCard) {
                            when (leftDays) {
                                0L, 1L -> Red
                                2L, 3L -> Orange
                                else -> Green
                            }
                        } else {
                            getLuminanceTextColor(Color(item.category.color))
                        },
                )
            )
        }

        Spacer(modifier = modifier.height(8.dp))

        IngredientAttrItem(
            modifier = modifier,
            icon = {
                Icon(
                    modifier = modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_3d_box),
                    contentDescription = stringResource(R.string.ingredient_card_view_icon_description),
                    tint = Color.Gray
                )
            },
            title = stringResource(R.string.count),
            content = item.count.toString()
        )

        IngredientAttrItem(
            modifier = modifier,
            icon = {
                Icon(
                    modifier = modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_refrigerator),
                    contentDescription = stringResource(R.string.ingredient_card_view_icon_description),
                    tint = Color.Gray
                )
            },
            title = stringResource(R.string.store_type),
            content = item.store.n
        )

        IngredientAttrItem(
            modifier = modifier,
            icon = {
                Icon(
                    modifier = modifier.size(20.dp),
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = stringResource(R.string.ingredient_card_view_icon_description),
                    tint = Color.Gray
                )
            },
            title = stringResource(R.string.expiration_date),
            content = item.expirationDate.toString()
        )
    }
}

@Composable
private fun IngredientAttrItem(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    title: String,
    content: String,
) {
    Row(
        modifier = modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        icon()

        Text(
            text = "$title : ",
            color = Color.Gray,
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            text = content,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview
@Composable
private fun IngredientCardPreview() {
    IngredientCard(
        item = Ingredient(
            name = "간장",
            count = 10,
            category = IngredientCategory.BEVERAGE,
            store = IngredientStore.REFRIGERATED,
            expirationDate = LocalDate.parse("2026-02-24")
        )
    )
}

@Preview
@Composable
private fun IconIngredientCardPreview() {
    IconIngredientCard(
        item = Ingredient(
            name = "사과",
            count = 10,
            category = IngredientCategory.FRUIT,
            store = IngredientStore.REFRIGERATED,
            expirationDate = LocalDate.parse("2026-02-26")
        ),
    )
}
