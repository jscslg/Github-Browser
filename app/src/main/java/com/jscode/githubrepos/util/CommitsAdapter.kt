package com.jscode.githubrepos.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jscode.githubrepos.databinding.BranchItemBinding
import com.jscode.githubrepos.databinding.CommitItemBinding
import com.jscode.githubrepos.model.BranchData
import com.jscode.githubrepos.model.CommitData

class CommitsAdapter: ListAdapter<CommitData, CommitsAdapter.ViewHolder>(CommitsAdapterDiff()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder(val binding: CommitItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommitData) {
            binding.item = item
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CommitItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
    companion object {
        class CommitsAdapterDiff: DiffUtil.ItemCallback<CommitData>() {
            override fun areItemsTheSame(oldItem: CommitData, newItem: CommitData): Boolean {
                return oldItem===newItem
            }

            override fun areContentsTheSame(oldItem: CommitData, newItem: CommitData): Boolean {
                return false
            }
        }
    }
}