package org.redaksi.ui.edisi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import org.redaksi.ui.Dimens.eight
import org.redaksi.ui.Dimens.sixteen
import org.redaksi.ui.PillarColor
import org.redaksi.ui.PillarTypography
import org.redaksi.ui.R
import org.redaksi.ui.Symbol
import org.redaksi.ui.model.IssueWithArticle

@Composable
fun EdisiScreen(
    viewmodel: EdisiViewModel = hiltViewModel()
) {
    Scaffold {
        val uiState by viewmodel.uiState.collectAsState()
        LazyColumn {
            item {
                EdisiLatest(issue = uiState.lastIssue)
            }
        }
    }
}

@Preview
@Composable
private fun EdisiScreenPreview() {
    EdisiScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EdisiLatest(issue: IssueWithArticle) {
    Card(
        Modifier
            .padding(sixteen.dp)
            .wrapContentHeight(),
        containerColor = PillarColor.edisiBackground
    ) {
        EdisiHeader(issue = issue)
        EdisiContent(issue = issue)
    }
}

@Composable
fun EdisiHeader(issue: IssueWithArticle) {
    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
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
            Text(style = PillarTypography.titleLarge, text = issue.number)
            Text(style = PillarTypography.bodySmall, text = issue.dateDisplay)
        }
        Box {
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
                painter = painterResource(id = R.drawable.image_pillar),
                contentDescription = "Gambar Pillar",
                modifier = Modifier
                    .size(128.dp)
                    .align(Alignment.CenterStart)
            )
        }
    }
}

@Composable
fun EdisiContent(issue: IssueWithArticle) {
    Column(modifier = Modifier.padding(sixteen.dp)) {
        Text(
            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, eight.dp),
            style = PillarTypography.titleSmall,
            text = issue.title
        )
        issue.articles.forEach { article ->
            ArtikelItem(article)
        }
    }
}

@Composable
fun ArtikelItem(title: String) {
    val modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 4.dp)
    val style = PillarTypography.bodyMedium
    Row(
        modifier = modifier
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

@Preview(
    name = "My Preview",
    showSystemUi = true
)
@Composable
private fun EdisiItemPreview() {
    EdisiLatest(
        IssueWithArticle(
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
