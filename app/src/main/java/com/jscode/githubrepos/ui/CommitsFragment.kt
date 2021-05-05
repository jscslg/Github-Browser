package com.jscode.githubrepos.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.jscode.githubrepos.R
import com.jscode.githubrepos.databinding.BranchesFragmentBinding
import com.jscode.githubrepos.databinding.CommitsFragmentBinding
import com.jscode.githubrepos.util.BranchesAdapter
import com.jscode.githubrepos.util.CommitsAdapter
import com.jscode.githubrepos.util.Status

class CommitsFragment : Fragment() {
    private lateinit var binding: CommitsFragmentBinding
    private lateinit var adapter: CommitsAdapter
    private val viewModel: MainViewModel by activityViewModels()
    private val args: CommitsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.commits_fragment,container,false)
        requireActivity().title = "Commits (${args.branch.name})"
        adapter = CommitsAdapter()
        binding.list.adapter = adapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getCommits(args.owner,args.name,args.branch.commit.sha).observe(viewLifecycleOwner,{
            it?.let { resource ->
                when(resource.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let { data-> adapter.submitList(data) }
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        viewModel.showError(resource.message)
                    }
                    Status.LOADING -> { }
                }
            }
        })
    }
}