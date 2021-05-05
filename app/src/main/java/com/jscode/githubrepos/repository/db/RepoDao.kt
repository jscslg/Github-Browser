package com.jscode.githubrepos.repository.db

import androidx.room.*
import com.jscode.githubrepos.model.RepoData
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {
    @Query("SELECT * FROM repo_table")
    fun getAllRepo(): Flow<List<RepoData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRepo(repo: RepoData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRepo(repo: RepoData)

    @Query("DELETE FROM repo_table WHERE id = :id")
    suspend fun deleteRepo(id:Long)

    @Query("DELETE from repo_table")
    suspend fun deleteAll()
}