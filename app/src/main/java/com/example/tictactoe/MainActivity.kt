package com.example.tictactoe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * [MainActivity] hosts the fragments for the Tic Tac Toe app.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        // Finds fragments associated with this activity
        // and casts selected fragment as NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = navHostFragment.navController

        /* Allows the action bar to automatically update the title as the destination changes.

        In order for the navigate button to know what to do, it must be told via either

        the AppBarConfiguration or AppCompatActivity.onSupportNavigateUp (As seen below) */

        setupActionBarWithNavController(navController)
    }

    // Short circuits result if navController.navigateUp() is valid
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}