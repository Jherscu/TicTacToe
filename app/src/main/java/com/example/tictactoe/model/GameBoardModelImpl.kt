package com.example.tictactoe.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Singleton of Game Board, extending [GameBoardModel] interface, with the ability to:
 * - Reset state
 * - Write to backing property
 * - Emit values on backing property changes
 */
object GameBoardModelImpl : GameBoardModel {
    // Creates backing property for gameBoard: making it read-only
    private val _gameBoard = MutableStateFlow(
        mutableListOf(
            mutableListOf("", "", ""),
            mutableListOf("", "", ""),
            mutableListOf("", "", "")
        )
    )

    // Tracks changes to game board within a state flow: automatically emitting results
    override val gameBoard: StateFlow<List<List<String>>> = _gameBoard

    override fun resetState() {
        for (list in _gameBoard.value) {
            list[0] = ""
            list[1] = ""
            list[2] = ""
        }
    }

    override fun addSymbol(x: Int, y: Int, symbol: String) {
        _gameBoard.value[x][y] = symbol
    }
}