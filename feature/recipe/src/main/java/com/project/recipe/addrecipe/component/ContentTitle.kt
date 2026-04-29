package com.project.recipe.addrecipe.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun ContentTitle(
    title: String
) {
    Text(
        modifier = Modifier.padding(vertical = 8.dp),
        text = title,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
internal fun IconContentTitle(
    title: String,
    icon: @Composable () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        icon()
        ContentTitle(title)
    }
}
