package com.project.recipe.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.unit.dp
import com.project.ui.IconResource
import com.project.ui.LocarmIcon

@Composable
internal fun RecipeInfoContent(
    modifier: Modifier = Modifier,
    time: Int,
    people: Int,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RecipeInfoContentItem(
            iconResource = IconResource.ImageVectorIcon(Icons.Default.AccessTime),
            title = "${time}분"
        )

        Spacer(modifier = Modifier.width(8.dp))

        RecipeInfoContentItem(
            iconResource = IconResource.PainterIcon(com.project.ui.R.drawable.ic_chef_hat),
            title = "${people}인분"
        )
    }
}

@Composable
private fun RecipeInfoContentItem(
    modifier: Modifier = Modifier,
    iconResource: IconResource,
    title: String,
) {
    LocarmIcon(
        modifier = modifier
            .size(16.dp),
        iconResource = iconResource,
        contentDescription = "",
        tint = Gray
    )

    Spacer(modifier = modifier.width(4.dp))

    Text(
        text = title,
        style = MaterialTheme.typography.bodySmall,
        color = Gray
    )
}
