package com.rulyadhika.tanamanio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rulyadhika.tanamanio.ui.MainScreenFragment
import com.rulyadhika.tanamanio.ui.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavBar:BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavBar = findViewById(R.id.bottom_navigation)

        val mainScreenFragment: Fragment = MainScreenFragment()
        val profileFragment: Fragment = ProfileFragment()

        setCurrentFragment(mainScreenFragment)

        bottomNavBar.setOnItemSelectedListener{
            when(it.itemId){
                R.id.home -> {
                    setCurrentFragment(mainScreenFragment)
                    true
                }
                R.id.profile->{
                    setCurrentFragment(profileFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_fragment, fragment)
            addToBackStack(null)
            commit()
        }
    }
}