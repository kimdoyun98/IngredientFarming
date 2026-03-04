package com.project.shopping_cart.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.designsystem.theme.Purple500
import com.project.designsystem.theme.Purple600
import com.project.designsystem.theme.ShoppingProgressTrackColor
import com.project.shopping_cart.R
import com.project.shopping_cart.util.getPercentage

@Composable
internal fun ShoppingProgress(
    modifier: Modifier = Modifier,
    cartCount: Int,
    buyCount: Int,
) {
    val animatedProgress by animateFloatAsState(
        targetValue = if (buyCount == 0) 0F else buyCount / cartCount.toFloat(),
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = stringResource(R.string.animated_progress_label)
    )

    val gradient = Brush.linearGradient(colors = listOf(Purple500, Purple600))

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(brush = gradient)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = modifier.size(28.dp),
                imageVector = Icons.Outlined.ShoppingBag,
                contentDescription = null,
                tint = White
            )

            Text(
                modifier = modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                text = stringResource(R.string.shopping_progress),
                style = MaterialTheme.typography.titleLarge,
                color = White,
            )

            Text(
                text = "${getPercentage(buyCount, cartCount)}%",
                style = MaterialTheme.typography.headlineSmall,
                color = White,
                fontWeight = FontWeight.Bold
            )
        }

        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .height(16.dp)
                .clip(CircleShape)
                .background(ShoppingProgressTrackColor)
        ) {
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxSize(),
                color = White,
                trackColor = ShoppingProgressTrackColor,
                strokeCap = StrokeCap.Round
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(
                    R.string.shopping_cart_progress_text_state,
                    buyCount,
                    cartCount
                ),
                style = MaterialTheme.typography.bodyLarge,
                color = White
            )
        }
    }
}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
private fun ShoppingProgressPreview() {
    ShoppingProgress(
        cartCount = 6,
        buyCount = 1
    )
}
