package com.project.home.model

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.project.designsystem.theme.Blue
import com.project.designsystem.theme.DeepOrange
import com.project.designsystem.theme.Green
import com.project.designsystem.theme.MoreLightBlue
import com.project.designsystem.theme.MoreLightGreen
import com.project.designsystem.theme.MoreLightOrange
import com.project.ui.R

data class HomeStatusCardInfo(
    val icon: @Composable () -> Unit,
    val iconBackgroundColor: Color,
    val count: Int,
    val title: String,
)

@Composable
fun rememberStatusCardInfo(): List<HomeStatusCardInfo> {
    val first = HomeStatusCardInfo(
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_apple),
                contentDescription = "Apple",
                tint = Green
            )
        },
        iconBackgroundColor = MoreLightGreen,
        count = 0,
        title = "보유 식재료"
    )

    val second = HomeStatusCardInfo(
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_clock),
                contentDescription = "Clock",
                tint = DeepOrange
            )
        },
        iconBackgroundColor = MoreLightOrange,
        count = 0,
        title = "곧 만료"
    )

    val third = HomeStatusCardInfo(
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_chef_hat),
                contentDescription = "Clock",
                tint = Blue
            )
        },
        iconBackgroundColor = MoreLightBlue,
        count = 0,
        title = "저장 레시피"
    )

    return remember {
        listOf(
            first,
            second,
            third
        )
    }
}
