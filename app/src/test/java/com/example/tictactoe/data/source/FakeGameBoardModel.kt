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

    /**
     * Sets test game board to multiple winning scenarios to setup for testing them.
     *
     * Set via either:
     *
     * x = {0|1|2}, _, direction = Horizontal
     *
     * _, y = {0|1|2}, direction = Vertical
     *
     * _, _, direction = {TopLeftToBottomRight|TopRightToBottomLeft}
     *
     * @param x Position on x axis
     *
     * @param y Position on y axis
     *
     * @param direction Direction class specifying which direction to test
     */
    fun TestWin(x: Int?, y: Int?, direction: Direction) {
        when (direction) {
            is Horizontal -> x?.apply { testWinHorizontal(x) }
                ?: throw IllegalArgumentException("Must declare X")
            is Vertical -> y?.apply { testWinVertical(y) }
                ?: throw IllegalArgumentException("Must declare Y")
            is DiagonalDirection -> testWinDiagonal(direction)
            else -> throw IllegalArgumentException("Please declare valid direction")
        }
    }

    private fun testWinHorizontal(x: Int) {
        _gameBoard.value[x] = mutableListOf("X", "X", "X")
    }

    private fun testWinVertical(y: Int) {
        _gameBoard.value[0][y] = "X"
        _gameBoard.value[1][y] = "X"
        _gameBoard.value[2][y] = "X"
    }

    private fun testWinDiagonal(direction: DiagonalDirection) {
        when (direction) {
            is TopLeftToBottomRight -> {
                _gameBoard.value[0][0] = "X"
                _gameBoard.value[1][1] = "X"
                _gameBoard.value[2][2] = "X"
            }
            is TopRightToBottomLeft -> {
                _gameBoard.value[0][2] = "X"
                _gameBoard.value[1][1] = "X"
                _gameBoard.value[2][0] = "X"
            }
        }
    }

    override fun addSymbol(x: Int, y: Int, symbol: String) {
        _gameBoard.value[x][y] = symbol
    }
}