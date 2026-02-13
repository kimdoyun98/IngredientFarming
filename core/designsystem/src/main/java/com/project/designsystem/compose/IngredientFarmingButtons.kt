package com.project.designsystem.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IngredientFarmingWideButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable () -> Unit
){
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = { onClick() },
        enabled = enabled,
        content = { content() }
    )
}

@Preview
@Composable
private fun IngredientFarmingWideButtonPreview(){
    IngredientFarmingWideButton(
        onClick = {}
    ){
        Text("다음")
    }
}
