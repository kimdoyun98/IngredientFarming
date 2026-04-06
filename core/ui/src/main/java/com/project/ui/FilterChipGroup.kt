package com.project.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.ui.util.rememberIngredientStoreImmutableList
import kotlinx.collections.immutable.ImmutableList

@Composable
fun FilterChipGroup(
    modifier: Modifier = Modifier,
    groupName: String? = null,
    groupList: ImmutableList<String>,
    onClick: (Int) -> Unit,
    selectedChipIndex: Int,
) {
    Column(
        modifier = modifier,
    ) {
        if (groupName != null) Text(text = groupName)

        FlowRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            groupList.forEachIndexed { idx, name ->
                FilterChip(
                    onClick = { onClick(idx) },
                    label = { Text(text = name) },
                    selected = idx == selectedChipIndex,
                    leadingIcon = if (idx == selectedChipIndex) {
                        {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Done icon",
                                modifier = Modifier
                                    .size(FilterChipDefaults.IconSize)
                            )
                        }
                    } else {
                        null
                    },
                )
            }
        }
    }
}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
private fun FilterChipGroupPreview() {
    FilterChipGroup(
        groupName = "보관방법",
        groupList = rememberIngredientStoreImmutableList(),
        onClick = {},
        selectedChipIndex = 1,
    )
}
