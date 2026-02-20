package com.project.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.outlined.AllInbox
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore
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

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = modifier.padding(start = 4.dp),
                    text = item.name,
                    fontSize = 18.sp
                )

                SuggestionChip(
                    onClick = { },
                    label = { Text(item.category.n) },
                )
            }

            IngredientAttrItem(
                modifier = modifier,
                icon = Icons.Outlined.AllInbox,
                title = "개수",
                content = item.count.toString()
            )
            
            IngredientAttrItem(
                modifier = modifier,
                icon = Icons.Outlined.Inventory2,
                title = "보관방법",
                content = item.store.n
            )

            IngredientAttrItem(
                modifier = modifier,
                icon = Icons.Default.CalendarToday,
                title = "유통기한",
                content = item.expirationDate.toString()
            )
        }
    }
}

@Composable
private fun IngredientAttrItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    content: String,
) {
    Row(
        modifier = modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = stringResource(R.string.ingredient_card_view_icon_description),
            tint = Color.Gray
        )

        Text(
            text = "$title : ",
            color = Color.Gray
        )

        Text(text = content)
    }
}

@Preview
@Composable
private fun IngredientCardPreview() {
    IngredientCard(
        item = Ingredient(
            name = "요구르트",
            count = 10,
            category = IngredientCategory.DAIRY,
            store = IngredientStore.REFRIGERATED,
            expirationDate = LocalDate.parse("2026-02-24")
        )
    )
}
