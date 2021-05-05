package com.jscode.githubrepos.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.jscode.githubrepos.R
import com.jscode.githubrepos.databinding.BranchesFragmentBinding
import com.jscode.githubrepos.util.BranchOnCLick
import com.jscode.githubrepos.util.BranchesAdapter
import com.jscode.githubrepos.util.IssuesAdapter
import com.jscode.githubrepos.util.Status

class BranchesFragment : Fragment() {
    private lateinit var binding: BranchesFragmentBinding
    private lateinit var adapter: BranchesAdapter
    private val viewModel: MainViewModel by activityViewModels()
    lateinit var owner: String
    lateinit var name: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.branches_fragment,container,false)
        owner = requireArguments().getString("owner","")
        name = requireArguments().getString("name","")
        adapter = BranchesAdapter(BranchOnCLick {
            view?.findNavController()?.navigate(RepoFragmentDirections.actionRepoFragmentToCommitsFragment(owner,name,it))
        })
        binding.list.adapter = adapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getBranches(owner,name).observe(viewLifecycleOwner,{
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