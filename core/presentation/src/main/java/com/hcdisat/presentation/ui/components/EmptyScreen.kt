package com.hcdisat.presentation.ui.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.hcdisat.presentation.R
import com.hcdisat.presentation.ui.theme.DimenDefault
import com.hcdisat.presentation.ui.theme.DisatNewsTheme

@Composable
fun EmptyScreen(modifier: Modifier = Modifier, errorMessage: String = "") {
    var icon = R.drawable.ic_network_error
    var message = errorMessage

    errorMessage.ifBlank {
        icon = R.drawable.ic_search_document
        message = stringResource(R.string.empty_screen_message)
    }

    EmptyContent(modifier = modifier, icon = icon, message = message)
}

@Composable
private fun EmptyContent(modifier: Modifier = Modifier, @DrawableRes icon: Int, message: String) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(120.dp),
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = .3f)
        )

        Spacer(modifier = Modifier.height(DimenDefault.smallPadding()))

        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = .3f)
            )
        )
    }
}

class EmptyScreenPreviewProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String>
        get() = sequenceOf(
            "Network Error",
            "Internet Unavailable",
            ""
        )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun EmptyScreenPreview(
    @PreviewParameter(EmptyScreenPreviewProvider::class) message: String
) {
    DisatNewsTheme {
        EmptyScreen(
            errorMessage = message
        )
    }
}