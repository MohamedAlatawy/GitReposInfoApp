package com.example.repos.feature_repositories.data.remote

import com.example.repos.feature_repositories.data.remote.dto.GitRepositoryDto
import com.example.repos.feature_repositories.data.remote.dto.RepoInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubRepositoriesApi {

    @GET("repositories")
    suspend fun getAllRepositories(): List<GitRepositoryDto>

    @GET("repos/{name}/{repo_name}")
    suspend fun getRepoInfo(
        @Path("name") name: String,
        @Path("repo_name") repoName: String,
    ): RepoInfoDto


    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

}