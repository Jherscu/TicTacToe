package com.example.tictactoe.data.source

import com.example.tictactoe.model.GameBoardModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object FakeGameBoardModel : GameBoardModel {

    // Direction class for use in TestWin() to test all winning patterns
    sealed class Direction

    open class AxialDirection : Direction()

    open class DiagonalDirection : Direction()

    class Vertical : AxialDirection()

    class Horizontal : AxialDirection()

    class TopLeftToBottomRight : DiagonalDirection()

    class TopRightToBottomLeft : DiagonalDirection()

    private val _gameBoard = MutableStateFlow(
        mutableListOf(
            mutableListOf("", "", ""),
            mutableListOf("", "", ""),
            mutableListOf("", "", "")
        )
    )

    override val gameBoard: StateFlow<List<List<String>>> = _gameBoard

    override fun resetState() {
        for (list in _gameBoard.value) {
            list[0] = ""
            list[1] = ""
            list[2] = ""
        }
    }

    // Overload function signature for different types of directions
    /**
     * Sets test game board to multiple winning scenarios to setup for testing them.
     *
     * Set via:
     *
     * row = {0|1|2}, direction = {Horizontal()|Vertical()}
     *
     * @param row Position on x or y axis
     *
     * @param direction Direction class specifying which direction to test
     */
    fun <T : AxialDirection> testWin(row: Int, direction: T) {
        when (direction) {
            is Horizontal -> testWinHorizontal(row)
            is Vertical -> testWinVertical(row)
        }
    }

    /**
     * Sets test game board to multiple winning scenarios to setup for testing them.
     *
     * Set via:
     *
     * direction = {TopLeftToBottomRight()|TopRightToBottomLeft()}
     *
     * @param direction Direction class specifying which direction to test
     */
    fun <T : DiagonalDirection> testWin(direction: T) {
        testWinDiagonal(direction)
    }

    private fun testWinHorizontal(x: Int) {
        _gameBoard.value[x] = mutableListOf("X", "X", "X")
    }

    private fun testWinVertical(y: Int) {
        addSymbol(1, y, "X")
        addSymbol(1, y, "X")
        addSymbol(1, y, "X")
    }

    private fun testWinDiagonal(direction: DiagonalDirection) {
        when (direction) {
            is TopLeftToBottomRight -> {
                addSymbol(0, 0, "X")
                addSymbol(1, 1, "X")
                addSymbol(2, 2, "X")
            }
            is TopRightToBottomLeft -> {
                addSymbol(0, 2, "X")
                addSymbol(1, 1, "X")
                addSymbol(2, 0, "X")
            }
        }
    }

    override fun addSymbol(x: Int, y: Int, symbol: String) {
        _gameBoard.value[x][y] = symbol
    }
}