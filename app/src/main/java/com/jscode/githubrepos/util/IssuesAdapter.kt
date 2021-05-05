package com.jscode.githubrepos.util

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jscode.githubrepos.databinding.IssueItemBinding
import com.jscode.githubrepos.model.IssueData


class IssuesAdapter: ListAdapter<IssueData, IssuesAdapter.ViewHolder>(BranchesAdapterDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder(private val binding: IssueItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: IssueData) {
            binding.item = item
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = IssueItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
    companion object{
        private val BranchesAdapterDiff = object : DiffUtil.ItemCallback<IssueData>() {
            override fun areItemsTheSame(oldItem: IssueData, newItem: IssueData): Boolean {
                return oldItem===newItem
            }

            override fun areContentsTheSame(oldItem: IssueData, newItem: IssueData): Boolean {
                return oldItem.id==newItem.id
            }
        }
    }
}