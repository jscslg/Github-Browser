package com.jscode.githubrepos.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jscode.githubrepos.R
import com.jscode.githubrepos.databinding.MainFragmentBinding
import com.jscode.githubrepos.util.RepoAdapter
import com.jscode.githubrepos.util.RepoOnClick

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: MainFragmentBinding
    private lateinit var adapter: RepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.action_add -> {
            view?.findNavController()?.navigate(R.id.action_mainFragment_to_repoAddFragment)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.main_fragment, container, false)
        requireActivity().title = "Github Browser"
        adapter = RepoAdapter(RepoOnClick({
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Github Repository Name: ${it.name}\nDescription: ${it.description}\nURL: ${it.url}")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        },{
            view?.findNavController()?.navigate(MainFragmentDirections.actionMainFragmentToRepoFragment(it))
        }))
        binding.repoList.adapter = adapter
        binding.repoList.layoutManager = LinearLayoutManager(activity)
        binding.addButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_mainFragment_to_repoAddFragment)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.repos.observe(viewLifecycleOwner,{
            it?.let {
                if(it.isNotEmpty()) binding.addLayout.visibility = View.GONE
                adapter.submitList(it)
            }
        })
    }

}