package com.hcdisat.presentation.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hcdisat.presentation.R
import com.hcdisat.presentation.ui.theme.DimenDefault
import com.hcdisat.presentation.ui.theme.DisatNewsTheme

@Composable
internal fun AppLogo(
    modifier: Modifier = Modifier,
    width: Dp = 150.dp,
    height: Dp = 30.dp
) {
    Image(
        painter = painterResource(id = R.drawable.ic_logo),
        contentDescription = "App Logo",
        modifier = modifier
            .height(height)
            .width(width)
            .padding(DimenDefault.tinyPadding())
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun AppLogoPreview() {
    DisatNewsTheme {
        AppLogo()
    }
}