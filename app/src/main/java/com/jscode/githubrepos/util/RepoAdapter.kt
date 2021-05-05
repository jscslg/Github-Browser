package com.jscode.githubrepos.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jscode.githubrepos.databinding.RepoItemBinding
import com.jscode.githubrepos.model.RepoData

class RepoAdapter(private val onClickHandler: RepoOnClick): ListAdapter<RepoData,RepoAdapter.ViewHolder>(RepoAdapterDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,onClickHandler)
    }

    class ViewHolder(private val binding: RepoItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RepoData, onClickHandler: RepoOnClick) {
            binding.item = item
            binding.onClickHandler = onClickHandler
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RepoItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
    companion object{
        private val RepoAdapterDiff = object : DiffUtil.ItemCallback<RepoData>() {
            override fun areItemsTheSame(oldItem: RepoData, newItem: RepoData): Boolean {
                return oldItem===newItem
            }

            override fun areContentsTheSame(oldItem: RepoData, newItem: RepoData): Boolean {
                return oldItem.id==newItem.id
            }
        }
    }
}
class RepoOnClick(private val iconClickListener :(data: RepoData)->Unit, private val repoClickListener :(data: RepoData)->Unit){
    fun onClickIcon(data: RepoData) = iconClickListener(data)
    fun onClickRepo(data: RepoData) = repoClickListener(data)
}

