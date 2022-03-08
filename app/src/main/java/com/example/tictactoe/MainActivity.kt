package com.example.tictactoe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.tictactoe.model.TicTacToeViewModel
import com.example.tictactoe.ui.GameFragment
import com.example.tictactoe.ui.GameFragmentDirections
import com.example.tictactoe.ui.dialog.WinningDialog

/**
 * [MainActivity] hosts the fragments for the Tic Tac Toe app.
 */
class MainActivity : AppCompatActivity(), WinningDialog.WinningDialogListener {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    // The following two methods can only be called from GameFragment via WinningDialog
    override fun onWinningDialogPositiveClick(
        dialog: DialogFragment,
        viewModel: TicTacToeViewModel,
        fragment: GameFragment
    ) {
        // Play again with the same names
        viewModel.resetGameState()
        // Refresh fragment from scratch so the UI reflects the cleared data
        fragment.parentFragmentManager.beginTransaction()
            .detach(fragment)
            .commit()
        fragment.parentFragmentManager.beginTransaction()
            .attach(fragment)
            .commit()
    }

    override fun onWinningDialogNegativeClick(
        dialog: DialogFragment,
        viewModel: TicTacToeViewModel
    ) {
        // Exit game and reset
        viewModel.resetGameState()
        navController.navigate(GameFragmentDirections.actionGameFragmentToLandingFragment())
    }
}