package com.jscode.githubrepos.repository.rest

import com.google.gson.GsonBuilder
import com.jscode.githubrepos.model.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.github.com"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
    .baseUrl(BASE_URL)
    .build()

interface RestApiService {
    @GET("/repos/{owner}/{name}")
    suspend fun getRepo(
        @Path(value = "owner",encoded = true) owner: String,
        @Path(value = "name",encoded = true) name:String
    ): RepoData

    @GET("/repos/{owner}/{name}/branches")
    suspend fun getBranches(
        @Path(value = "owner",encoded = true) owner:String,
        @Path(value = "name",encoded = true) name:String
    ): List<BranchData>

    @GET("/repos/{owner}/{name}/issues")
    suspend fun getIssues(
        @Path(value = "owner",encoded = true) owner:String,
        @Path(value = "name",encoded = true) name:String,
        @Query(value = "state") state:String="open"
    ): List<IssueData>

    @GET("/repos/{owner}/{name}/commits")
    suspend fun getCommits(
        @Path(value = "owner",encoded = true) owner:String,
        @Path(value = "name",encoded = true) name:String,
        @Query(value = "sha") sha:String
    ): List<CommitData>
}

object RestApi {
    val retrofitService : RestApiService by lazy {
        retrofit.create(RestApiService::class.java)
    }
}