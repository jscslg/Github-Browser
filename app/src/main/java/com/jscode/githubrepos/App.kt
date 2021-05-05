package com.jscode.githubrepos

import android.app.Application
import com.jscode.githubrepos.repository.db.RepoDatabase
import com.jscode.githubrepos.repository.RepoRepository

class App: Application() {
    val database by lazy { RepoDatabase.getDatabase(this) }
    val repository by lazy { RepoRepository(database.repoDao()) }
}