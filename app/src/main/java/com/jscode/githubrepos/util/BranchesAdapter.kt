package com.jscode.githubrepos.util

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jscode.githubrepos.databinding.BranchItemBinding
import com.jscode.githubrepos.model.BranchData
import com.jscode.githubrepos.model.IssueData


class BranchesAdapter(private val onClickHandler: BranchOnCLick): ListAdapter<BranchData, BranchesAdapter.ViewHolder>(BranchesAdapterDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,onClickHandler)
    }

    class ViewHolder(private val binding: BranchItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BranchData, onClickHandler: BranchOnCLick) {
            binding.item = item
            binding.onClickHandler = onClickHandler
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BranchItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
    companion object{
        private val BranchesAdapterDiff = object : DiffUtil.ItemCallback<BranchData>() {
            override fun areItemsTheSame(oldItem: BranchData, newItem: BranchData): Boolean {
                return oldItem===newItem
            }

            override fun areContentsTheSame(oldItem: BranchData, newItem: BranchData): Boolean {
                return oldItem.name==newItem.name
            }
        }
    }
}
class BranchOnCLick(private val clickListener :(data: BranchData)->Unit){
    fun onClickBranch(data: BranchData) = clickListener(data)
}