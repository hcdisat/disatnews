package com.hcdisat.onboarding.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.hcdisat.onboarding.model.Page
import com.hcdisat.onboarding.model.pages
import com.hcdisat.presentation.ui.theme.DisatNewsTheme

@Composable
internal fun OnboardingPage(modifier: Modifier = Modifier, page: Page) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = .6f),
            painter = painterResource(id = page.stepImage),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = OnboardingDimen.mediumPadding())
                .padding(horizontal = OnboardingDimen.largePadding()),
        ) {
            Text(
                text = page.title,
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = com.hcdisat.presentation.R.color.display_small)
                )
            )
            Text(
                text = page.description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = .7f)
                )
            )
        }
    }
}

@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES, name = "night")
@Preview(showSystemUi = true)
@Composable
private fun OnboardingPagePreview() {
    DisatNewsTheme {
        OnboardingPage(page = pages[0])
    }
}