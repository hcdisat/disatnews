package com.hcdisat.presentation.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hcdisat.presentation.R
import com.hcdisat.presentation.ui.model.Article
import com.hcdisat.presentation.ui.theme.DimenDefault
import com.hcdisat.presentation.ui.theme.DisatNewsTheme
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ArticleItem(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(DimenDefault.xSmallPadding())
    ) {
        AsyncImage(
            modifier = Modifier
                .size(96.dp)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(LocalContext.current).data(article.urlToImage).build(),
            contentDescription = "${article.title} by ${article.author}",
            placeholder = painterResource(R.drawable.ic_android_black_placeholder),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceAround) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontSize = 16.sp,
                    lineHeight = 16.sp
                ),
                color = colorResource(id = R.color.text_title)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(end = DimenDefault.smallPadding()),
                    text = article.sourceName,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(id = R.color.body)
                )
                DateInfo(dateTime = article.publishedAt)
            }
        }
    }
}

@Composable
fun DateInfo(
    modifier: Modifier = Modifier,
    dateTime: String,
    dateFormat: String = "MMM, dd y 'at' hh:mm a"
) {
    val displayDate by remember {
        derivedStateOf {
            ZonedDateTime.parse(dateTime)
                .format(DateTimeFormatter.ofPattern(dateFormat))
        }
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(DimenDefault.tinyPadding()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = null,
            modifier = Modifier.size(11.dp),
            tint = colorResource(id = R.color.body)
        )
        Text(
            text = displayDate,
            style = MaterialTheme.typography.labelSmall.copy(fontStyle = FontStyle.Italic),
            color = colorResource(id = R.color.body),
            textDecoration = TextDecoration.Underline
        )
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun ArticleItemPreview() {
    DisatNewsTheme {
        ArticleItem(
            article = Article(
                author = "Karissa Bell",
                title = "SEC approves bitcoin ETFs (for real this time)",
                description = """
                    The Securities and Exchange Commission has approved\r\n
                    the applications of 11 spot bitcoin ETFs in a highly anticipated
                    decision that will make it much easier for people to dabble in
                    cryptocurrency investing without directly buying and holding bitcoin. The app…
                """.trimIndent(),
                url = "https://www.engadget.com/sec-approves-bitcoin-etfs-for-real-this-time-224125584.html",
                urlToImage = "https://s.yimg.com/ny/api/res/1.2/n6iLNJ_9dtK.fT6WAXK1sA--/YXBwaWQ9aGlnaGxhbmRlcjt3PTEyMDA7aD03OTU-/https://s.yimg.com/os/creatr-uploaded-images/2024-01/3edf5140-afdd-11ee-bf7c-7918e1b9d963",
                publishedAt = "2024-01-10T22:41:25Z",
                content = """
                    The Securities and Exchange Commission has approved\r\n
                    the applications of 11 spot bitcoin ETFs in a highly anticipated decision that will make it much easier for people to dabble in cryptocurrency in… [+1453 chars]
                """.trimIndent(),
                sourceId = "fox-news",
                sourceName = "FOX News"
            )
        )
    }
}