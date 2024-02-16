package com.example.indianpincodeapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.indianpincodeapp.Adapter.ItemSetAdapter
import com.example.indianpincodeapp.Model.Location
import com.example.indianpincodeapp.Model.State
import com.example.indianpincodeapp.databinding.FragmentAreaSearchBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class AreaSearchFragment : Fragment() {
    private lateinit var binding: FragmentAreaSearchBinding
    private lateinit var mRef: DatabaseReference
    var stateList = mutableListOf<State>()
    private lateinit  var mAdapter: ItemSetAdapter
    private var addressList = mutableListOf<Location>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAreaSearchBinding.inflate(inflater, container, false)
        mRef = Firebase.database.reference
        mAdapter = ItemSetAdapter(requireContext(), addressList,mRef, false){ itemId ->
        }
        binding.recycleview.layoutManager = LinearLayoutManager(requireContext())
        binding.recycleview.adapter = mAdapter
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRef = Firebase.database.reference
        mRef.child("state").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                stateList.clear()
                for(snap in snapshot.children){
                   var state= snap.getValue(State::class.java)
                   state?.let {
                       stateList.add(it)
                   }
                }
                val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, stateList)
               binding.autoState.setAdapter(adapter)
                binding.autoState.onItemSelectedListener= object : OnItemSelectedListener{
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        var state = stateList[position]
                        loadDistrictList(state)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        binding.search.setOnClickListener {
            val selectedDistrict = binding.autoDistrict.selectedItem.toString()
            val selectedCity = binding.autoCity.selectedItem.toString()
            val filteredList = addressList.filter { it.district == selectedDistrict || it.city == selectedCity }

            mAdapter.updateList(filteredList,true)
        }
        binding.clear.setOnClickListener {
            binding.autoState.setSelection(0)
            binding.autoDistrict.setSelection(0)
            binding.autoCity.setSelection(0)

            // Clear adapter data
            mAdapter.UpdateList(emptyList(), false)
        }
    }



    private fun loadDistrictList(state: State) {
        var districts = state.districts as List<String>
        var districtAdapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,districts)
        binding.autoDistrict.setAdapter(districtAdapter)
        districtAdapter.notifyDataSetChanged()

        binding.autoDistrict.onItemSelectedListener= object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var district = districts[position]
                Log.d("SelectedDistrict", district)
                loadCity(district)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun loadCity(district: String) {
        addressList.clear()
        mRef.child("address").orderByChild("district").equalTo(district).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    val uniqueCities:MutableSet<String> = mutableSetOf<String>()
                    for(snap in snapshot.children){
                        var address = snap.getValue(Location::class.java)
                        address?.let {
                            if (uniqueCities.add(it.city!!))
                                addressList.add(it)
                        }
                    }

                    var adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, addressList.map { it.city })
                    binding.autoCity.adapter = adapter

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("TAG", "onCancelled: ")
                }

            })

    }


    private fun ItemSetAdapter.UpdateList(mutableList: List<Location>,displayData: Boolean = true) {
        this.addresslist = mutableList.toMutableList()
        this.displayData=displayData
        for (location in mutableList) {
            fetchLikeStatusFromFirebase(location)
        }

        notifyDataSetChanged()
    }

}