package com.project.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.model.ingredient.ExpirationDateSoonIngredient
import com.project.model.ingredient.IngredientCategory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

@Composable
fun ExpirationDateSoonBox(
    modifier: Modifier = Modifier,
    items: ImmutableList<ExpirationDateSoonIngredient>
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp))
            .shadow(elevation = 8.dp),
    ) {
        LazyColumn(
            modifier = modifier
                .padding(4.dp)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            itemsIndexed(
                items = items,
                key = { _, item -> item.id }
            ) { idx, item ->
                ExpirationDateSoonItem(
                    modifier = modifier,
                    item = item
                )

                if (items.size > 1 && idx < items.lastIndex) {
                    HorizontalDivider(
                        thickness = 0.5.dp,
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
private fun TestPreview() {
    ExpirationDateSoonBox(
        modifier = Modifier,
        items = persistentListOf(
            ExpirationDateSoonIngredient(
                id = 0,
                name = "사과",
                category = IngredientCategory.FRUIT,
                expirationDate = LocalDate.now().plusDays(2)
            ),
            ExpirationDateSoonIngredient(
                id = 1,
                name = "사과",
                category = IngredientCategory.FRUIT,
                expirationDate = LocalDate.now().plusDays(2)
            )
        )
    )
}
