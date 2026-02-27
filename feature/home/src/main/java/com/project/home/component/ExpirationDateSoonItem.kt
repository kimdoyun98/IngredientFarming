package com.project.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.model.ingredient.ExpirationDateSoonIngredient
import com.project.model.ingredient.IngredientCategory
import com.project.ui.CategorySmallIconBox
import com.project.ui.util.daysLeft
import java.time.LocalDate

@Composable
fun ExpirationDateSoonItem(
    modifier: Modifier = Modifier,
    item: ExpirationDateSoonIngredient,
) {
    val daysLeft = remember(item.expirationDate) { daysLeft(item.expirationDate) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CategorySmallIconBox(
            modifier = modifier,
            category = item.category
        )

        Column(
            modifier = modifier
                .weight(1F)
                .padding(start = 12.dp)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = item.category.n,
                color = Color.Gray
            )
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "D-$daysLeft",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Red
            )
            Text(
                text = "${daysLeft}일 남음",
                color = Color.Gray
            )
        }
    }
}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
private fun ExpirationDateSoonContentPreview() {
    ExpirationDateSoonItem(
        modifier = Modifier,
        item = ExpirationDateSoonIngredient(
            id = 1,
            name = "사과",
            category = IngredientCategory.FRUIT,
            expirationDate = LocalDate.now().plusDays(2)
        )
    )
}
