package com.project.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.designsystem.theme.DeepOrange
import com.project.designsystem.theme.MoreLightOrange

@Composable
fun StatusCard(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    iconBackgroundColor: Color,
    count: Int,
    title: String,
) {
    Card(
        modifier = modifier
            .size(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        border = BorderStroke(1.dp, Color.Gray),
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = modifier
                    .size(40.dp)
                    .border(
                        width = 1.dp,
                        color = iconBackgroundColor,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = iconBackgroundColor),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }

            Text(
                modifier = modifier
                    .padding(top = 10.dp, bottom = 2.dp),
                text = "$count",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = title,
                fontSize = 12.sp,
                color = Color.DarkGray
            )
        }
    }
}

@Preview
@Composable
private fun StatusCardPreview() {
    StatusCard(
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_clock),
                contentDescription = "Clock",
                tint = DeepOrange
            )
        },
        iconBackgroundColor = MoreLightOrange,
        count = 34,
        title = "유통기한 임박"
    )
}
