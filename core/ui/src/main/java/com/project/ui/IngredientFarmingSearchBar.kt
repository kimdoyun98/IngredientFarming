package com.project.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IngredientFarmingSearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onCloseClick: () -> Unit,
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        value = query,
        onValueChange = { newValue -> onQueryChange(newValue) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon"
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onCloseClick() }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear search"
                    )
                }
            }
        },
        singleLine = true,
    )
}

@Preview
@Composable
private fun IngredientFarmingSearchPreview() {
    IngredientFarmingSearchBar(
        query = "Example search",
        onQueryChange = {},
        onCloseClick = {}
    )
}
