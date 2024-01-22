package com.hcdisat.onboarding.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hcdisat.onboarding.model.NavigatorLabels
import com.hcdisat.presentation.NightDayPreview
import com.hcdisat.presentation.ui.components.AppButton
import com.hcdisat.presentation.ui.components.Indicator
import com.hcdisat.presentation.ui.theme.DisatNewsTheme

@Composable
internal fun Navigator(
    modifier: Modifier = Modifier,
    labels: NavigatorLabels,
    size: Int,
    selectedIndex: Int = 0,
    onNext: () -> Unit = {},
    onBack: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = OnboardingDimen.smallPadding(),
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OnboardingIndicator(
            size = size,
            selectedIndex = selectedIndex,
            modifier = Modifier.weight(1f)
        )

        AppButton(
            text = labels.backText,
            containerColor = Color.Transparent,
            onClick = onBack
        )

        AppButton(
            text = labels.nextText,
            containerColor = MaterialTheme.colorScheme.primary,
            onClick = onNext
        )
    }
}

@Composable
private fun OnboardingIndicator(
    modifier: Modifier = Modifier,
    size: Int,
    selectedIndex: Int = 0
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(size) {
            Indicator(isSelected = it == selectedIndex)
        }
    }
}

@NightDayPreview
@Composable
private fun NavigatorPreview() {
    DisatNewsTheme {
        Navigator(
            selectedIndex = 0,
            size = 3,
            labels = NavigatorLabels("Back", "Next")
        )
    }
}