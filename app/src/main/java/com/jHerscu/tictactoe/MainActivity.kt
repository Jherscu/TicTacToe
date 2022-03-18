package com.jHerscu.tictactoe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.jHerscu.tictactoe.model.TicTacToeViewModel
import com.jHerscu.tictactoe.ui.GameFragment
import com.jHerscu.tictactoe.ui.GameFragmentDirections
import com.jHerscu.tictactoe.ui.dialog.WinningDialog
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * [MainActivity] hosts the fragments for the Tic Tac Toe app.
 */
class MainActivity : AppCompatActivity(), WinningDialog.WinningDialogListener {

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

    // The following two methods can only be called from GameFragment via WinningDialog
    override fun onWinningDialogPositiveClick(
        dialog: DialogFragment,
        viewModel: TicTacToeViewModel,
        fragment: GameFragment
    ) {
        // Temporarily save state for use in reinstating Fragment with the same names
        val playerOne = viewModel.playerOneName.value!!
        val playerTwo = viewModel.playerTwoName.value!!

        // Refresh fragment from scratch so the UI reflects the cleared data
        viewModel.resetGameState()
        fragment.findNavController()
            .navigate(
                GameFragmentDirections.actionGameFragmentSelf(
                    playerOneName = playerOne,
                    playerTwoName = playerTwo
                )
            )
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