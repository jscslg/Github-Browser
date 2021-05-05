package com.jscode.githubrepos.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "repo_table")
data class RepoData(
    var owner: String="",
    var name:String="",
    @Expose val description: String="",
    @Expose @SerializedName("open_issues") val issuesCount: Long,
    @Expose @SerializedName("html_url") val url: String,
    @Expose @PrimaryKey(autoGenerate = false) val id:Long
) : Parcelable