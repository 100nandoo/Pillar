package org.redaksi.ui.edisi

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import org.redaksi.data.remote.response.base.Issue

@Composable
fun EdisiScreen(
    viewmodel: EdisiViewModel = hiltViewModel()
) {
    Scaffold {
        val uiState by viewmodel.uiState.collectAsState()
        LazyColumn {
            uiState.issues.forEach {
                item {
                    EdisiItem(issue = it)
                }
            }
        }
    }
}

@Preview
@Composable
private fun EdisiScreenPreview() {
    EdisiScreen()
}

@Composable
fun EdisiItem(issue: Issue){
    Column {
        Text(issue.issueNumber)
        Text(issue.monthDisplay)
    }
}

@Preview(
    name = "My Preview",
    showSystemUi = true
)
@Composable
private fun EdisiItemPreview() {
    EdisiItem(Issue("233", "202204", "Apr 2022", "", "", ""))
}
