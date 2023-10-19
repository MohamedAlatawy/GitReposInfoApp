package com.example.repos.feature_repositories.data.remote

import com.example.repos.feature_repositories.data.remote.dto.GitRepositoryDto
import com.example.repos.feature_repositories.data.remote.dto.RepoInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubRepositoriesApi {

    @GET("repositories")
    suspend fun getAllRepositories(): List<GitRepositoryDto>

    @GET("repos/{full_name}")
    suspend fun getRepoInfo(
        @Path("full_name") fullName: String
    ): RepoInfoDto


    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

}