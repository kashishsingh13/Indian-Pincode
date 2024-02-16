package com.example.indianpincodeapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.indianpincodeapp.AreaSearchFragment
import com.example.indianpincodeapp.SavesRecordFragment
import com.example.indianpincodeapp.SearchFragment

class FragementAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3 // Number of tabs
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchFragment()
            1 -> AreaSearchFragment()
            2 -> SavesRecordFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}