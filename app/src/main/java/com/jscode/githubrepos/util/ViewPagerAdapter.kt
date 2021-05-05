package com.jscode.githubrepos.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jscode.githubrepos.model.BranchData
import com.jscode.githubrepos.model.IssueData
import com.jscode.githubrepos.ui.BranchesFragment
import com.jscode.githubrepos.ui.IssuesFragment
import java.lang.IllegalStateException

class ViewPagerAdapter(fragment: Fragment,val owner: String,val name: String) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> {
            val fragment = BranchesFragment()
            fragment.arguments = Bundle().apply {
                putString("owner",owner)
                putString("name",name)
            }
            fragment
        }
        1 -> {
            val fragment = IssuesFragment()
            fragment.arguments = Bundle().apply {
                putString("owner",owner)
                putString("name",name)
            }
            fragment
        }
        else -> throw IllegalStateException("Invalid tab number")
    }

}