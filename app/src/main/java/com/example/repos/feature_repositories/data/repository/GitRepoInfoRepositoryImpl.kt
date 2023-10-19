package com.example.repos.feature_repositories.data.repository

import com.example.repos.core.util.Resource
import com.example.repos.feature_repositories.data.local.GitRepoInfoDao
import com.example.repos.feature_repositories.data.local.entity.GitRepoInfoEntity
import com.example.repos.feature_repositories.data.remote.GithubRepositoriesApi
import com.example.repos.feature_repositories.domain.model.GitRepoInfo
import com.example.repos.feature_repositories.domain.repository.GitRepoInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GitRepoInfoRepositoryImpl @Inject constructor(
    private val api: GithubRepositoriesApi,
    private val dao: GitRepoInfoDao
) : GitRepoInfoRepository {


    override fun getGitReposInfo(offset: Int): Flow<Resource<List<GitRepoInfo?>>> = flow {

        emit(Resource.Loading())

        val gitReposInfo = dao.getGitRepos(offset).map { it?.toGitRepoInfo() }
        emit(Resource.Loading(data = gitReposInfo))

        try {

            val remoteGitRepos = api.getAllRepositories()
            val remoteGitRepoInfo = remoteGitRepos.map { item ->
                api.getRepoInfo(item.full_name)
            }

            val gitRepoInfoEntity = remoteGitRepos.mapIndexed { index, obj1 ->

                val obj2 = remoteGitRepoInfo[index]
                GitRepoInfoEntity(
                    id = obj1.id,
                    repoName = obj1.name,
                    owner = obj1.owner.toOwner(),
                    repoInfo = obj2.toRepoInfo()
                )
            }
            dao.insertGitRepos(gitRepoInfoEntity)

        } catch (e: HttpException) {

            emit(Resource.Error(
                message = "Oops something went wrong !",
                data = gitReposInfo
            ))
        } catch (e: IOException) {

            emit(Resource.Error(
                message = "Couldn't reach server, check your internet connection.",
                data = gitReposInfo
            ))
        }

        val newGitRepoInfo = dao.getGitRepos(offset).map { it?.toGitRepoInfo() }
        emit(Resource.Success(newGitRepoInfo))

    }

}












