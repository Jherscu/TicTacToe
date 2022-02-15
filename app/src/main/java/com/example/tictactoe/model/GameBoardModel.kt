package com.example.tictactoe.model

import kotlinx.coroutines.flow.StateFlow

/**
 * Interface of the game board that can be overridden for
 * implementation or testing purposes.
 */
interface GameBoardModel {
    /**
     * Instance of game board
     */
    val gameBoard: StateFlow<List<List<String>>>

    /**
     * Initializes and later resets the state of the game board
     */
    fun resetState()

    /**
     * Marks either an "X" or "O" on the game board
     *
     * @param x The horizontal indentation
     *
     * @param y The vertical indentation
     *
     * @param symbol Either the "X" or "O"
     */
    fun addSymbol(x: Int, y: Int, symbol: String)
}