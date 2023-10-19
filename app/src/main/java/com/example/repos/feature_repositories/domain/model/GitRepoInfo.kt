package com.example.repos.feature_repositories.domain.model

data class GitRepoInfo(
    val id: Int,
    val repoName: String,
    val owner: Owner,
    val repoInfo: RepoInfo
)
