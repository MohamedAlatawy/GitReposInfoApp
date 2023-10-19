package com.example.repos.feature_repositories.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.repos.feature_repositories.data.local.entity.GitRepoInfoEntity


@Database(
    entities = [GitRepoInfoEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class GitRepoInfoDatabase: RoomDatabase() {

    abstract val dao: GitRepoInfoDao
}