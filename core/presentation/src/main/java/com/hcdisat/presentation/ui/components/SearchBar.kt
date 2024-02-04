package com.hcdisat.presentation.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hcdisat.presentation.R
import com.hcdisat.presentation.ui.theme.DimenDefault
import com.hcdisat.presentation.ui.theme.DisatNewsTheme

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String = "",
    readOnly: Boolean = false,
    onClick: () -> Unit = {},
    onSearch: () -> Unit = {},
    onChange: (String) -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isClicked = interactionSource.collectIsPressedAsState().value
    LaunchedEffect(key1 = isClicked, block = {
        if (isClicked) {
            onClick()
        }
    })

    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier.then(Modifier.searchBoxBorder()),
        readOnly = readOnly,
        shape = MaterialTheme.shapes.medium,
        singleLine = true,
        textStyle = MaterialTheme.typography.bodySmall,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch() }),
        interactionSource = interactionSource,
        placeholder = {
            Text(
                "Search",
                color = colorResource(id = R.color.placeholder),
                style = MaterialTheme.typography.bodySmall
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = colorResource(id = R.color.body)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(id = R.color.input_background),
            unfocusedContainerColor = colorResource(id = R.color.input_background),
            focusedTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
            unfocusedTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
            cursorColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
    )
}

private fun Modifier.searchBoxBorder() = composed {
    if (!isSystemInDarkTheme()) {
        border(
            width = 1.dp,
            color = Color.Black,
            shape = MaterialTheme.shapes.medium
        )
    } else {
        this
    }
}

@Preview(showBackground = true, name = "Light Mode")
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES, name = "Dark Mode")
@Composable
fun SearchBarPreview() {
    var value by remember { mutableStateOf("") }
    DisatNewsTheme {
        SearchBar(
            value = value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(DimenDefault.smallPadding()),
            onClick = {
                Log.d("SearchBarPreview", "SearchBarPreview: onClick")
            },
        ) {
            value = it
        }
    }
}