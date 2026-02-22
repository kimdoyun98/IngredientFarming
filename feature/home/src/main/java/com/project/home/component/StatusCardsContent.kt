package com.project.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.project.home.model.HomeStatusCardInfo
import com.project.home.model.rememberStatusCardInfo
import com.project.ui.StatusCard
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun StatusCardsContent(
    modifier: Modifier = Modifier,
    statusCardInfo: ImmutableList<HomeStatusCardInfo> = rememberStatusCardInfo(),
    ingredientCount: Int,
    expiresSoonCount: Int,
    recipeCount: Int,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        items(statusCardInfo.size) { idx ->
            StatusCard(
                icon = statusCardInfo[idx].icon,
                iconBackgroundColor = statusCardInfo[idx].iconBackgroundColor,
                count = when (idx) {
                    0 -> ingredientCount
                    1 -> expiresSoonCount
                    2 -> recipeCount
                    else -> 0
                },
                title = statusCardInfo[idx].title
            )
        }
    }
}

@Preview
@Composable
private fun StatusCardsContentPreview() {
    StatusCardsContent(
        ingredientCount = 5,
        expiresSoonCount = 2,
        recipeCount = 3
    )
}
