package com.example.tictactoe.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel for [com.example.tictactoe.MainActivity]
 *
 * Contains logic for a Tic Tac Toe game.
 */
class TicTacToeViewModel : ViewModel() {

    private val _playerOneName = MutableLiveData<String>()
    val playerOneName: LiveData<String> = _playerOneName

    private val _playerTwoName = MutableLiveData<String>()
    val playerTwoName: LiveData<String> = _playerTwoName

    /**
     * Updates viewModel properties with safeArgs name input from
     * [com.example.tictactoe.ui.LandingFragment]
     *
     * @param player The player: 1 ( 'X' ) or 2 ( 'O' )
     *
     * @param name The optional name passed instead of the defaults of 'X' and 'O'
     */
    fun updatePlayerName(player: Int, name: String) {
        when (player) {
            1 -> _playerOneName.value = name
            2 -> _playerTwoName.value = name
        }
    }

}