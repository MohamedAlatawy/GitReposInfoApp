package com.example.repos.feature_repositories.di

import android.app.Application
import androidx.room.Room
import com.example.repos.feature_repositories.data.local.Converters
import com.example.repos.feature_repositories.data.local.GitRepoInfoDao
import com.example.repos.feature_repositories.data.local.GitRepoInfoDatabase
import com.example.repos.feature_repositories.data.remote.GithubRepositoriesApi
import com.example.repos.feature_repositories.data.remote.GithubRepositoriesApi.Companion.BASE_URL
import com.example.repos.feature_repositories.data.repository.GitRepoInfoRepositoryImpl
import com.example.repos.feature_repositories.data.util.GsonParser
import com.example.repos.feature_repositories.data.util.JsonParser
import com.example.repos.feature_repositories.domain.repository.GitRepoInfoRepository
import com.example.repos.feature_repositories.domain.use_case.GetGitRepos
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object GitRepoModule {

    @Provides
    @Singleton
    fun provideGetGitReposUseCase(repository: GitRepoInfoRepository): GetGitRepos {
        return GetGitRepos(repository)
    }

    @Provides
    @Singleton
    fun provideGitRepoRepository(
        db: GitRepoInfoDatabase,
        api: GithubRepositoriesApi
    ): GitRepoInfoRepository {
        return GitRepoInfoRepositoryImpl(api, db.dao)
    }

    @Provides
    @Singleton
    fun provideGitRepoInfoDatabase(app: Application): GitRepoInfoDatabase {
        return Room.databaseBuilder(app,GitRepoInfoDatabase::class.java, "git_repos_db")
            .addTypeConverter(Converters(GsonParser(Gson())))
            .build()
    }

    @Provides
    @Singleton
    fun provideGitRepoApi(): GithubRepositoriesApi {

        val token =  "Bearer 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"
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Authorization",
                        token )
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubRepositoriesApi::class.java)
    }

}







