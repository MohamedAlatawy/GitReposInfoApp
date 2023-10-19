package com.example.repos.feature_repositories.presentation

import com.example.repos.feature_repositories.domain.model.GitRepoInfo

sealed class GitRepoListEvent{

    object FirstPageEvent: GitRepoListEvent()

    object NextPageEvent: GitRepoListEvent()

}

data class GitRepoState(
    val gitRepoItems: List<GitRepoInfo?> = emptyList(),
    val isLoading: Boolean = false
)


