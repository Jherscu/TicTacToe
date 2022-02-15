package com.example.tictactoe.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// A conscious decision has been made to use StateFlow in this area vs
// the LiveData used in the viewModel prior to writing this.
// I want to practice using both in this test app.
/**
 * Singleton of Game Board, extending [GameBoardModel] interface, with the ability to:
 * - Reset state
 * - Write to backing property
 * - Emit values on backing property changes
 */
object GameBoardModelImpl: GameBoardModel {
    // Creates backing property for gameBoard: making it read-only
    private lateinit var _gameBoard: MutableStateFlow<MutableList<MutableList<String>>>
    // Tracks changes to game board within a state flow: automatically emitting results
    override val gameBoard: StateFlow<List<List<String>>> = _gameBoard

    override fun resetState() {
        _gameBoard = MutableStateFlow(
            mutableListOf(
                mutableListOf("","",""),
                mutableListOf("","",""),
                mutableListOf("","","")
            )
        )
    }

    override fun addSymbol(x: Int, y: Int, symbol: String) {
        _gameBoard.value[x][y] = symbol
    }
}