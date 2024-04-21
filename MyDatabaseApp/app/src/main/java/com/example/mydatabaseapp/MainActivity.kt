package com.example.mydatabaseapp


import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.setViewNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.mydatabaseapp.fragments.list.ListFragment
import com.example.mydatabaseapp.viewmodel.UserViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<View>(R.id.fragmentContainerView)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)

        //NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        navController.navInflater


        setViewNavController(view = view, controller = navController)
        setSupportActionBar(toolbar)
        setupActionBarWithNavController(findNavController(R.id.fragmentContainerView))
        findNavController(R.id.fragmentContainerView)

        //AppBar
//        val appBarConfiguration = AppBarConfiguration(
//            topLevelDestinationIds = setOf(),
//            fallbackOnNavigateUpListener = ::onSupportNavigateUp
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)


    }

    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

//    fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.delete_menu, menu)
//    }



}
