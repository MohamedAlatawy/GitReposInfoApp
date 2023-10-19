package com.example.repos.feature_repositories.domain.repository

import com.example.repos.core.util.Resource
import com.example.repos.feature_repositories.domain.model.GitRepoInfo
import kotlinx.coroutines.flow.Flow

interface GitRepoInfoRepository {

    fun getGitReposInfo(offset: Int ): Flow<Resource<List<GitRepoInfo?>>>

}