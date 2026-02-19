package com.project.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.project.designsystem.compose.IngredientFarmingRoundedIconButton
import com.project.designsystem.theme.DeepOrange
import com.project.designsystem.theme.MoreLightBlue
import com.project.designsystem.theme.MoreLightGreen
import com.project.designsystem.theme.MoreLightOrange
import com.project.designsystem.theme.MoreLightPink
import com.project.ui.R


@Composable
internal fun NavigateButtonsContent(
    modifier: Modifier = Modifier,
    onManageButtonClick: () -> Unit,
    onAddButtonClick: () -> Unit,
    onRecipeButtonClick: () -> Unit,
    onShoppingCartButtonClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        NavigateButton(
            modifier = modifier,
            title = stringResource(com.project.home.R.string.manage),
            backgroundColor = MoreLightBlue,
            onClick = { onManageButtonClick() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_3d_box),
                contentDescription = null,
                tint = Color.Blue
            )
        }

        NavigateButton(
            modifier = modifier,
            title = stringResource(com.project.home.R.string.add),
            backgroundColor = MoreLightOrange,
            onClick = { onAddButtonClick() }
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = null,
                tint = DeepOrange
            )
        }

        NavigateButton(
            modifier = modifier,
            title = stringResource(com.project.home.R.string.recipe),
            backgroundColor = MoreLightGreen,
            onClick = { onRecipeButtonClick() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_chef_hat),
                contentDescription = null,
                tint = Color.Green
            )
        }

        NavigateButton(
            modifier = modifier,
            title = stringResource(com.project.home.R.string.shopping_cart),
            backgroundColor = MoreLightPink,
            onClick = { onShoppingCartButtonClick() }
        ) {
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = null,
                tint = Color.Magenta
            )
        }
    }
}

@Composable
private fun NavigateButton(
    modifier: Modifier = Modifier,
    title: String,
    backgroundColor: Color,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IngredientFarmingRoundedIconButton(
            modifier = modifier,
            backgroundColor = backgroundColor,
            onClick = { onClick() }
        ) {
            icon()
        }

        Text(title)
    }
}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true,
)
@Composable
private fun NavigateButtonsPreview() {
    NavigateButtonsContent(
        onManageButtonClick = {},
        onAddButtonClick = {},
        onRecipeButtonClick = {},
        onShoppingCartButtonClick = {}
    )
}
