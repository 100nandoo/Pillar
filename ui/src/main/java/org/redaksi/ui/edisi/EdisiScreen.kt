package org.redaksi.ui.edisi

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.valentinilk.shimmer.shimmer
import org.redaksi.ui.Dimens.eight
import org.redaksi.ui.Dimens.sixteen
import org.redaksi.ui.PillarColor
import org.redaksi.ui.PillarTypography
import org.redaksi.ui.R
import org.redaksi.ui.Symbol

@Composable
fun EdisiScreen(
    onClick: (issueNumber: String) -> Unit,
) {
    Scaffold {
        val viewModel: EdisiViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsState()
        SwipeRefresh(
            state = rememberSwipeRefreshState(uiState.isLoading),
            onRefresh = { viewModel.loadEdisi() }) {
            LazyColumn {
                if (uiState.isLoading) {
                    item {
                        HeaderItem(Modifier.shimmer(), R.string.terbaru)
                    }
                    item {
                        EdisiItem(Modifier.shimmer(), { onClick("") }, IssueUi("", "", "", listOf("", "", "", "", "", "", "")))
                    }
                    item {
                        HeaderItem(Modifier.shimmer(), R.string.sebelumnya)
                    }
                    item {
                        EdisiItem(Modifier.shimmer(), { onClick("") }, IssueUi("", "", "", listOf("", "", "", "", "", "", "")))
                    }
                }
                uiState.issuesUi.forEachIndexed { index, issueWithArticle ->
                    if (index == 0) {
                        item {
                            HeaderItem(Modifier, R.string.terbaru)
                        }
                        item {
                            EdisiItem(issue = issueWithArticle, onClick = { onClick(issueWithArticle.number) })
                        }
                        item {
                            HeaderItem(Modifier, R.string.sebelumnya)
                        }
                    } else {
                        item {
                            EdisiItem(issue = issueWithArticle, onClick = { onClick(issueWithArticle.number) })
                        }
                    }

                }
            }
        }
    }
}

@Preview
@Composable
private fun EdisiScreenPreview() {
    EdisiScreen {}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EdisiItem(modifier: Modifier = Modifier, onClick: () -> Unit, issue: IssueUi) {
    @Composable
    fun EdisiHeader(modifier: Modifier, issue: IssueUi) {
        Row(modifier = modifier.height(IntrinsicSize.Min)) {
            Column(
                modifier = Modifier
                    .background(PillarColor.edisiNumber)
                    .padding(sixteen.dp)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logos),
                    contentDescription = "Logo logos"
                )
                Text(modifier = modifier, style = PillarTypography.titleLarge, text = issue.number)
                Text(modifier = modifier, style = PillarTypography.bodySmall, text = issue.dateDisplay)
            }
            Box(modifier = Modifier) {
                Column(
                    modifier = Modifier
                        .background(PillarColor.edisiTitle)
                        .fillMaxSize()
                        .padding(sixteen.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(textAlign = TextAlign.End, text = stringResource(id = R.string.buletin_grii))
                    Text(style = PillarTypography.titleMedium, text = "Pillar")
                }
                Icon(
                    modifier = Modifier
                        .size(128.dp)
                        .align(Alignment.CenterStart),
                    painter = painterResource(id = R.drawable.image_pillar),
                    contentDescription = "Gambar Pillar"

                )
            }
        }
    }

    @Composable
    fun EdisiContent(modifier: Modifier, issue: IssueUi) {
        @Composable
        fun ArtikelItem(modifier: Modifier, title: String) {
            val staticModifier = modifier.padding(0.dp, 0.dp, 0.dp, 4.dp)
            val style = PillarTypography.bodyMedium
            Row(
                modifier = staticModifier
            ) {
                Text(
                    style = style,
                    text = Symbol.bullet + " "
                )
                Text(
                    style = style,
                    text = title
                )
            }
        }

        Column(modifier = modifier.padding(sixteen.dp)) {
            if (issue.title.isNotBlank()) {
                Text(
                    modifier = modifier.padding(0.dp, 0.dp, 0.dp, eight.dp),
                    style = PillarTypography.titleSmall,
                    text = issue.title
                )
            }
            issue.articles.forEach { article ->
                ArtikelItem(modifier, article)
            }
        }
    }

    Card(
        Modifier
            .padding(sixteen.dp, sixteen.dp, sixteen.dp, 0.dp)
            .wrapContentHeight()
            .clickable { onClick() },
        containerColor = PillarColor.edisiBackground
    ) {
        EdisiHeader(modifier = modifier, issue = issue)
        EdisiContent(modifier = modifier, issue = issue)
    }
}

@Preview(
    showBackground = true
)
@Composable
private fun EdisiItemPreview() {
    EdisiItem(
        Modifier,
        { },
        IssueUi(
            "224",
            "Apr 2022",
            "Iman, Pengharapan, dan Kasih (Bagian 14): Doktrin Iman",
            listOf(
                "Doktrin Wahyu: Sebuah Introduksi",
                "The Doctrine of Revelation (2): The Condescension of the World",
                "Doktrin Wahyu: Sebuah Introduksi"
            )
        )
    )
}

@Composable
fun HeaderItem(modifier: Modifier, @StringRes id: Int) {
    val staticModifier = modifier
        .padding(sixteen.dp, sixteen.dp, sixteen.dp, 0.dp)
        .fillMaxWidth()
    Text(modifier = staticModifier, style = PillarTypography.titleLarge, text = stringResource(id = id))
}

@Preview(
    name = "HeaderItem",
    showSystemUi = true
)
@Composable
private fun HeaderItemPreview() {
    Column {
        HeaderItem(Modifier, R.string.terbaru)
        HeaderItem(Modifier, R.string.sebelumnya)

        HeaderItem(Modifier.shimmer(), R.string.terbaru)
        HeaderItem(Modifier.shimmer(), R.string.sebelumnya)
    }
}
