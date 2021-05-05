package com.jscode.githubrepos.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.jscode.githubrepos.R
import com.jscode.githubrepos.databinding.BranchesFragmentBinding
import com.jscode.githubrepos.databinding.IssuesFragmentBinding
import com.jscode.githubrepos.util.IssuesAdapter
import com.jscode.githubrepos.util.Status

class IssuesFragment : Fragment() {
    private lateinit var binding: IssuesFragmentBinding
    private lateinit var adapter: IssuesAdapter
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.issues_fragment,container,false)
        adapter = IssuesAdapter()
        binding.list.adapter = adapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val owner = requireArguments().getString("owner","")
        val name = requireArguments().getString("name","")
        viewModel.getIssues(owner,name).observe(viewLifecycleOwner,{
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