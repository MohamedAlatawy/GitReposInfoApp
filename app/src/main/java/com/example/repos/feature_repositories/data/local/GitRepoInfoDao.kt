package com.example.repos.feature_repositories.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.repos.feature_repositories.data.local.entity.GitRepoInfoEntity


@Dao
interface GitRepoInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGitRepos(repos: List<GitRepoInfoEntity>)

    @Query("DELETE FROM gitrepoinfoentity")
    suspend fun deleteGitRepos()

    @Query("SELECT * FROM gitrepoinfoentity LIMIT 10 OFFSET:offset")
    suspend fun getGitRepos(offset: Int ): List<GitRepoInfoEntity?>

}