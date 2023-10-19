package com.example.repos.feature_repositories.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.repos.feature_repositories.domain.model.GitRepoInfo

@Composable
fun GitRepoItemCard(
    gitRepoInfo: GitRepoInfo,
) {

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {


           LoadAvatar(url = gitRepoInfo.owner.avatarUrl)

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(12.dp)
            ) {
                Text(
                    text = "Repository name : ${gitRepoInfo.repoName}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.Start),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Owner name : ${gitRepoInfo.owner.ownerName}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.Start),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Repository creation date : ${gitRepoInfo.repoInfo.formatDate(gitRepoInfo.repoInfo.creationDate)}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.Start),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}