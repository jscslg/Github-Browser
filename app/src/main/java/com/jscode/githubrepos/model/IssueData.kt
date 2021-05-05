package com.jscode.githubrepos.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class IssueData(
    @Expose val title: String,
    @Expose val user: IssueUserData,
    @Expose val id: Long
): Parcelable

@Parcelize
data class IssueUserData(
    @Expose @SerializedName("login") val name: String,
    @Expose @SerializedName("avatar_url") val url: String
): Parcelable
