package com.jscode.githubrepos.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class BranchData(
    @Expose val name:String,
    @Expose val commit: BranchCommitData
): Parcelable

@Parcelize
data class BranchCommitData(
    @Expose val sha: String
): Parcelable
