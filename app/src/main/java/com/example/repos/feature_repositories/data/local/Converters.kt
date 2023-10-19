package com.example.repos.feature_repositories.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.repos.feature_repositories.data.util.JsonParser
import com.example.repos.feature_repositories.domain.model.Owner
import com.example.repos.feature_repositories.domain.model.RepoInfo
import com.google.gson.reflect.TypeToken


@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {

    @TypeConverter
    fun fromOwnerJson(json: String) : Owner {
        return jsonParser.fromJson<Owner>(
            json,
            object : TypeToken<Owner>(){}.type
        ) ?: Owner("",0,"")
    }

    @TypeConverter
    fun toOwnerJson(owner: Owner) : String {
        return jsonParser.toJson(
            owner,
            object : TypeToken<Owner>(){}.type
        ) ?: ""
    }


    @TypeConverter
    fun fromRepoInfoJson(json: String) : RepoInfo {
        return jsonParser.fromJson<RepoInfo>(
            json,
            object : TypeToken<RepoInfo>(){}.type
        ) ?: RepoInfo("",0)
    }

    @TypeConverter
    fun toRepoInfoJson(repoInfo: RepoInfo) : String {
        return jsonParser.toJson(
            repoInfo,
            object : TypeToken<Owner>(){}.type
        ) ?: ""
    }

}