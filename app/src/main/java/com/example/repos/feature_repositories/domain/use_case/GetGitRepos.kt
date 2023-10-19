package com.example.repos.feature_repositories.domain.use_case

import com.example.repos.core.util.Resource
import com.example.repos.feature_repositories.domain.model.GitRepoInfo
import com.example.repos.feature_repositories.domain.repository.GitRepoInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGitRepos @Inject constructor (
    private val repository: GitRepoInfoRepository
) {

    operator fun invoke(offset: Int = 0): Flow<Resource<List<GitRepoInfo?>>> {
        return repository.getGitReposInfo(offset)
    }

}