package com.jscode.githubrepos.repository

import androidx.annotation.WorkerThread
import com.jscode.githubrepos.repository.db.RepoDao
import com.jscode.githubrepos.model.RepoData
import com.jscode.githubrepos.repository.rest.RestApi
import kotlinx.coroutines.flow.Flow

class RepoRepository(private val repoDao: RepoDao) {
    val repos: Flow<List<RepoData>> = repoDao.getAllRepo()

    @WorkerThread
    suspend fun getRepo(owner: String, name: String) = RestApi.retrofitService.getRepo(owner,name)

    @WorkerThread
    suspend fun getBranches(owner: String, name: String) = RestApi.retrofitService.getBranches(owner,name)

    @WorkerThread
    suspend fun getIssues(owner: String, name:String) = RestApi.retrofitService.getIssues(owner,name)

    @WorkerThread
    suspend fun getCommits(owner: String, name:String, sha:String) = RestApi.retrofitService.getCommits(owner,name,sha)

    @WorkerThread
    suspend fun insertRepo(repo: RepoData) = repoDao.insertRepo(repo)

    @WorkerThread
    suspend fun updateRepo(repo: RepoData) = repoDao.updateRepo(repo)

    @WorkerThread
    suspend fun deleteRepo(id: Long) = repoDao.deleteRepo(id)

    @WorkerThread
    suspend fun deleteAll() = repoDao.deleteAll()
}