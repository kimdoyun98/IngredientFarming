package com.project.ingredient_manage.defaultingredient.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.designsystem.theme.LightOrange
import com.project.designsystem.theme.MoreLightOrange
import com.project.designsystem.theme.Orange
import com.project.designsystem.theme.Pink40
import com.project.designsystem.theme.Silver
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore
import com.project.ui.modifier.shadowLayout
import com.project.ui.modifier.singleClickable

@Composable
internal fun DefaultIngredientItem(
    modifier: Modifier = Modifier,
    name: String,
    category: IngredientCategory,
    store: IngredientStore,
    isComplete: Boolean,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadowLayout()
            .then(
                if (isComplete) Modifier
                else Modifier
                    .border(
                        width = 1.dp,
                        color = Orange,
                        shape = RoundedCornerShape(16.dp)
                    )
            )
            .singleClickable(onClick),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 20.dp),
        ) {
            if (!isComplete) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MoreLightOrange,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = LightOrange,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Default.ErrorOutline,
                        contentDescription = null,
                        tint = Orange
                    )

                    Text(
                        text = "정보 수정 필요",
                        color = Pink40,
                        style = MaterialTheme.typography.labelSmall

                    )
                }
            }

            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                IconButton(
                    onClick = onClick
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.NavigateNext,
                        contentDescription = null,
                        tint = LightGray
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "카테고리",
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray
                )

                DefaultIngredientLabel(
                    title = category.title,
                    isComplete = isComplete
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "보관위치",
                    style = MaterialTheme.typography.bodySmall,
                    color = Gray
                )

                DefaultIngredientLabel(
                    title = store.title,
                    isComplete = isComplete
                )
            }
        }
    }
}

@Composable
private fun DefaultIngredientLabel(
    title: String,
    isComplete: Boolean
) {
    Text(
        modifier = Modifier
            .background(
                color = if (isComplete) Silver else MoreLightOrange,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = if (isComplete) Silver else MoreLightOrange,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 6.dp, horizontal = 16.dp),
        text = title,
        style = MaterialTheme.typography.labelSmall
    )
}


@Preview(
    backgroundColor = 0XFFFFFF,
    showBackground = true
)
@Composable
internal fun CompleteDefaultIngredientItemPreview() {
    DefaultIngredientItem(
        name = "사과",
        category = IngredientCategory.FRUIT,
        store = IngredientStore.ROOM_TEMPERATURE,
        isComplete = true,
        onClick = {}
    )
}

@Preview(
    backgroundColor = 0XFFFFFF,
    showBackground = true
)
@Composable
internal fun InCompleteDefaultIngredientItemPreview() {
    DefaultIngredientItem(
        name = "사과",
        category = IngredientCategory.FRUIT,
        store = IngredientStore.ROOM_TEMPERATURE,
        isComplete = false,
        onClick = {}
    )
}
