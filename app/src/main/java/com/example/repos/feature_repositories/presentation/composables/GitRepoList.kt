package com.example.repos.feature_repositories.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.repos.core.util.PAGE_SIZE
import com.example.repos.feature_repositories.domain.model.GitRepoInfo
import com.example.repos.feature_repositories.presentation.GitRepoListEvent
import com.example.repos.feature_repositories.presentation.GitRepoState

@Composable
fun GitRepoList(
    paddingValues: PaddingValues,
    gitRepoState: GitRepoState,
    onChangeGitRepoScrollPosition: (Int) -> Unit,
    page: Int,
    onNextPage: (GitRepoListEvent) -> Unit,
){

    val scrollState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ){
        LazyColumn(
            modifier = Modifier.padding(5.dp),
            state = scrollState
        ) {
            // We use itemsIndexed to get the index of each item
            itemsIndexed(gitRepoState.gitRepoItems) { index, item ->
                onChangeGitRepoScrollPosition(index)
                if ((index + 1) >= (page * PAGE_SIZE) && !gitRepoState.isLoading){
                    onNextPage(GitRepoListEvent.NextPageEvent)
                }
                if (item != null) {
                    GitRepoItemCard(
                        gitRepoInfo = item,
                    )
                }
            }
        }
        CircularProgressBar(isDisplayed = gitRepoState.isLoading)
    }

}