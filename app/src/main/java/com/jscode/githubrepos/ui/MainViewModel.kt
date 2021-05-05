package com.jscode.githubrepos.ui

import android.util.Log
import androidx.lifecycle.*
import com.google.android.material.snackbar.Snackbar
import com.jscode.githubrepos.model.BranchData
import com.jscode.githubrepos.model.IssueData
import com.jscode.githubrepos.model.RepoData
import com.jscode.githubrepos.repository.RepoRepository
import com.jscode.githubrepos.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val repo: RepoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MainViewModel(private val repository: RepoRepository) : ViewModel() {
    private val _snack = MutableLiveData<String>()
    val snack: LiveData<String>
        get() = _snack
    val repos: LiveData<List<RepoData>> = repository.repos.asLiveData()

    fun getRepo(owner: String,name: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.getRepo(owner,name)))
        } catch (e : Exception){
            emit(Resource.error(null, (e.message?:"Something went wrong").toString()))
        }
    }

    fun validateRepo(owner: String, name: String): Boolean {
        if(owner == "" || name == "") {
            _snack.value = "${if(owner=="") "owner" else "name"} cannot be blank"
            return false
        }
        return true
    }

    fun insertRepo(data: RepoData) = viewModelScope.launch {
        repository.insertRepo(data)
    }

    fun deleteRepo(id: Long) = viewModelScope.launch {
        repository.deleteRepo(id)
    }

    fun updateRepo(repo: RepoData) = viewModelScope.launch {
        repository.updateRepo(repo)
    }

    fun getBranches(owner: String,name: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val data = repository.getBranches(owner,name)
            emit(Resource.success(data))
        } catch (e : Exception){
            emit(Resource.error(null, (e.message?:"Something went wrong").toString()))
        }
    }

    fun getIssues(owner: String,name: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.getIssues(owner,name)))
        } catch (e : Exception){
            emit(Resource.error(null, (e.message?:"Something went wrong").toString()))
        }
    }

    fun getCommits(owner: String, name: String, sha: String) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.getCommits(owner,name,sha)))
        } catch (e : Exception){
            emit(Resource.error(null, (e.message?:"Something went wrong").toString()))
        }
    }

    fun showError(message: String?) {
        message?.let {
            Log.e("Error", it)
            _snack.value = when(it.trim()) {
                "HTTP 404" -> "Repo Not Found"
                "HTTP 403" -> "Forbidden by the Server"
                else -> "Something Went Wrong"
            }
        }
    }
}