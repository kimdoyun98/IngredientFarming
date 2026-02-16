package com.project.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.home.component.StatusCardsContent
import com.project.ui.AppBarType
import com.project.ui.IngredientFarmingTopAppBar

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    IngredientFarmingTopAppBar(
        title = "냉장고 지킴이",
        type = AppBarType.None
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatusCardsContent(
                ingredientCount = 5,
                expiresSoonCount = 2,
                recipeCount = 3
            )

        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
