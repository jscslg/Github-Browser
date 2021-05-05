package com.jscode.githubrepos.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CommitData(
    @Expose val commit: CommitMetaData,
    @Expose val author: AuthorMetadata
)

data class CommitMetaData(
    @Expose val message: String,
    @Expose val author: CommitAuthor
)
data class AuthorMetadata(
    @Expose @SerializedName("avatar_url") val url: String
)

data class CommitAuthor(
    @Expose val name: String,
    @Expose val date: String
)
