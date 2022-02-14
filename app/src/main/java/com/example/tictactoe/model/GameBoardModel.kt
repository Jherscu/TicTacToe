package com.example.tictactoe.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// A conscious decision has been made to use StateFlow in this area vs
// the LiveData used in the viewModel prior to writing this.
// I want to practice using both in this test app.
/**
 * Singleton of Game Board with the ability to:
 * - Reset state
 * - Write to backing property
 * - Emit values on backing property changes
 */
object GameBoardModel {
    // Creates backing property for gameBoard: making it read-only
    private lateinit var _gameBoard: MutableList<MutableList<String>>
    // Tracks changes to game board within a state flow: automatically emitting results
    val gameBoard: StateFlow<List<List<String>>> = MutableStateFlow(_gameBoard)

    /**
     * Initializes and later resets the state of the game board
     */
    fun resetState() {
        _gameBoard = mutableListOf(
            mutableListOf("","",""),
            mutableListOf("","",""),
            mutableListOf("","","")
        )
    }

    /**
     * Marks either an "X" or "O" on the game board
     *
     * @param x The horizontal indentation
     *
     * @param y The vertical indentation
     *
     * @param symbol Either the "X" or "O"
     */
    fun addSymbol(x: Int, y: Int, symbol: String) {
        _gameBoard[x][y] = symbol
    }
}