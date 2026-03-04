package com.project.shopping_cart.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.project.designsystem.theme.Green
import com.project.designsystem.theme.MoreLightGreen
import com.project.model.ingredient.IngredientCategory
import com.project.shopping_cart.R
import com.project.ui.modifier.shadowLayout

@Composable
internal fun ShoppingCartItem(
    modifier: Modifier = Modifier,
    name: String,
    count: Int,
    category: IngredientCategory,
    success: Boolean,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadowLayout(1)
            .then(
                if (success) {
                    Modifier
                        .background(MoreLightGreen)
                        .border(
                            width = 1.dp,
                            color = Green,
                            shape = RoundedCornerShape(16.dp)
                        )
                } else {
                    Modifier
                }
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Checkbox(
            modifier = modifier,
            onCheckedChange = { onClick() },
            checked = success
        )

        Column(
            modifier = modifier.weight(1f)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleLarge.copy(
                    textDecoration =
                        if (success) TextDecoration.LineThrough
                        else TextDecoration.None
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = category.n,
                    style = MaterialTheme.typography.labelLarge,
                    color = if (success) Green else Gray
                )

                Icon(
                    modifier = modifier.size(4.dp),
                    imageVector = Icons.Default.Circle,
                    contentDescription = null,
                    tint = if (success) Green else Gray,
                )

                Text(
                    text = stringResource(R.string.shopping_cart_item_count, count),
                    style = MaterialTheme.typography.labelLarge,
                    color = if (success) Green else Gray
                )
            }
        }

        IconButton(
            onClick = onDeleteClick
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = Gray
            )
        }
    }
}
