package com.example.indianpincodeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.indianpincodeapp.Adapter.FragementAdapter
import com.example.indianpincodeapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)

        val viewPager: ViewPager2 = binding.viewpager
        val tabLayout: TabLayout = binding.tablayout

        val adapter = FragementAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        // Connect the TabLayout with the ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "QUICK SEARCH"
                1 -> tab.text = "SEARCH BY AREA"
                2 -> tab.text = "SAVED RECORDS"
            }
        }.attach()


    }

}