package com.example.indianpincodeapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.indianpincodeapp.Adapter.ItemSetAdapter
import com.example.indianpincodeapp.Model.Location
import com.example.indianpincodeapp.databinding.FragmentSavesRecordBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class SavesRecordFragment : Fragment() {
    private lateinit var binding:FragmentSavesRecordBinding
    private lateinit var adapter: ItemSetAdapter
    private var addresslist= mutableListOf<Location>()
    private lateinit var mRef: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSavesRecordBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRef= Firebase.database.reference
        adapter = ItemSetAdapter(requireContext(),addresslist, mRef,true){ itemId ->

        }
        binding.recycleview.layoutManager= LinearLayoutManager(context)
        binding.recycleview.adapter = adapter

        adapter.fetchAllLikeStatusFromFirebase()
        for (location in addresslist) {
            adapter.fetchLikeStatusFromFirebase(location)
        }

        adapter.notifyDataSetChanged()

    }

}