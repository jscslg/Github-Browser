package com.jscode.githubrepos.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.jscode.githubrepos.R
import com.jscode.githubrepos.databinding.RepoFragmentBinding
import com.jscode.githubrepos.util.Status
import com.jscode.githubrepos.util.ViewPagerAdapter

class RepoFragment : Fragment() {
    private val args: RepoFragmentArgs by navArgs()
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: RepoFragmentBinding
    var dataArrived = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.repo_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.action_delete -> {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Are you sure you want to delete this repository ?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes") { _, _ ->
                    viewModel.deleteRepo(args.data.id)
                    view?.findNavController()?.navigateUp()
                }
                .show()
            true
        }
        R.id.action_view -> {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(args.data.url))
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding =  DataBindingUtil.inflate(layoutInflater,R.layout.repo_fragment,container,false)
        binding.lifecycleOwner = this
        requireActivity().title = "Details"
        binding.repo = args.data
        val adapter = ViewPagerAdapter(this,args.data.owner,args.data.name)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Branches"
                1 -> tab.text = "Issues (${args.data.issuesCount})"
            }
        }.attach()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getRepo(args.data.owner,args.data.name).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when(resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { data->
                            data.owner = args.data.owner
                            data.name = args.data.name
                            if(data.description!=args.data.description || data.issuesCount!=args.data.issuesCount) {
                                viewModel.updateRepo(data)
                                if(data.issuesCount!=args.data.issuesCount) binding.tabs.getTabAt(1)?.text = "Issues (${data.issuesCount})"
                                if(data.description!=args.data.description) binding.repo = data
                            }
                        }
                    }
                    Status.ERROR -> {
                        viewModel.showError(resource.message)
                    }
                    Status.LOADING -> {}
                }
            }
        })
    }

}