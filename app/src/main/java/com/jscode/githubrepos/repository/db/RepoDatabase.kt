package com.jscode.githubrepos.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jscode.githubrepos.model.RepoData

@Database(entities = [RepoData::class], version = 5, exportSchema = false)
abstract class RepoDatabase: RoomDatabase() {
    abstract fun repoDao(): RepoDao

    companion object{
        @Volatile
        private var INSTANCE: RepoDatabase?=null

        fun getDatabase(context: Context): RepoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, RepoDatabase::class.java,"repo_database").fallbackToDestructiveMigration()
                    .build()
                INSTANCE =instance
                instance
            }
        }
    }
}