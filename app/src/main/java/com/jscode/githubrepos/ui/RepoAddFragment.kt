package com.jscode.githubrepos.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jscode.githubrepos.R
import com.jscode.githubrepos.databinding.RepoAddFragmentBinding
import com.jscode.githubrepos.util.Status

class RepoAddFragment : Fragment() {
    private lateinit var binding: RepoAddFragmentBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.repo_add_fragment, container, false)
        requireActivity().title = "Add Repo"
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.addButton.setOnClickListener {
            val owner = binding.ownerText.text.toString().trim()
            val name = binding.repoText.text.toString().trim()
            if(viewModel.validateRepo(owner,name)) viewModel.getRepo(owner,name).observe(viewLifecycleOwner,{
                it?.let { resource ->
                    when(resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { data->
                                Log.i("SUCCESS",data.toString())
                                data.owner = owner
                                data.name = name
                                viewModel.insertRepo(data)
                            }
                            view?.findNavController()?.navigateUp()
                        }
                        Status.ERROR -> {
                            viewModel.showError(resource.message)
                            binding.ownerText.isEnabled = true
                            binding.repoText.isEnabled = true
                        }
                        Status.LOADING -> {
                            binding.ownerText.isEnabled = false
                            binding.repoText.isEnabled = false
                        }
                    }
                }
            })
        }
    }

}