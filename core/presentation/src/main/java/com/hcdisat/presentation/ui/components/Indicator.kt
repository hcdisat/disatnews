package com.hcdisat.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hcdisat.presentation.NightDayPreview
import com.hcdisat.presentation.ui.theme.Blue
import com.hcdisat.presentation.ui.theme.DisatNewsTheme
import com.hcdisat.presentation.ui.theme.WhiteGray

@Composable
fun Indicator(
    isSelected: Boolean = false,
    size: Dp = 16.dp,
    color: Color = WhiteGray,
    selectedColor: Color = Blue
) {
    val indicatorColor = if (isSelected) selectedColor else color
    Box(
        modifier = Modifier
            .size(size)
            .background(color = indicatorColor, shape = CircleShape)
    )
}

@NightDayPreview
@Composable
private fun IndicatorPreview() {
    DisatNewsTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            repeat(3) {
                Indicator(isSelected = it == 0)
            }
        }
    }
}