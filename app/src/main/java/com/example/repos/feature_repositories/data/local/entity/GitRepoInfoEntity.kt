package com.example.repos.feature_repositories.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.repos.feature_repositories.domain.model.GitRepoInfo
import com.example.repos.feature_repositories.domain.model.Owner
import com.example.repos.feature_repositories.domain.model.RepoInfo


@Entity
data class GitRepoInfoEntity(
    @PrimaryKey(autoGenerate = true) val index: Int? = null,
    var id: Int,
    var repoName: String,
    var owner: Owner,
    var repoInfo: RepoInfo
) {
    fun toGitRepoInfo(): GitRepoInfo {
        return GitRepoInfo(
            id = id,
            repoName = repoName,
            owner = owner,
            repoInfo = repoInfo
        )
    }
}










