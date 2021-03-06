package com.jHerscu.tictactoe.model

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.*
import com.jHerscu.tictactoe.R
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * ViewModel for [com.jHerscu.tictactoe.MainActivity]
 *
 * Contains logic for a Tic Tac Toe game.
 */
class TicTacToeViewModel : ViewModel() {

    private val _playerOneName = MutableLiveData("")
    val playerOneName: LiveData<String> = _playerOneName

    private val _playerTwoName = MutableLiveData("")
    val playerTwoName: LiveData<String> = _playerTwoName

    // Tracks when a win occurs
    private val _isWin = MutableLiveData(false)
    val isWin: LiveData<Boolean> = _isWin

    private val _winningPlayer = MutableLiveData("")
    val winningPlayer: LiveData<String> = _winningPlayer

    // Tracks whose turn it is to play
    private val _currentPlayer = MutableLiveData("X")
    val currentPlayer: LiveData<String> = _currentPlayer

    init {
        GameBoardModelImpl.resetState()
    }

    val gameBoard = GameBoardModelImpl.gameBoard.asLiveData()

    /**
     * Updates viewModel properties with safeArgs name input from
     * [com.jHerscu.tictactoe.ui.LandingFragment]
     *
     * @param player The player: 1 ( "X" ) or 2 ( "O" )
     *
     * @param name The optional name passed instead of the defaults of "X" and "O"
     */
    fun updatePlayerName(player: Int, name: String) {
        when (player) {
            1 -> _playerOneName.value = name
            2 -> _playerTwoName.value = name
        }
    }

    /**
     * If the game has been won, the [_winningPlayer] value is updated to reflect the winner
     * and [_isWin] is set to true.
     *
     * @param winningPlayer The string representing the winning player "X" or "O"
     */
    private fun announceWinner(winningPlayer: String) {
        when (winningPlayer) {
            "X" -> _winningPlayer.value =
                if (playerOneName.value.isNullOrBlank()) winningPlayer else playerOneName.value
            "O" -> _winningPlayer.value =
                if (playerTwoName.value.isNullOrBlank()) winningPlayer else playerTwoName.value
        }
        _isWin.value = true
    }

    /**
     *  Reads the publicly exposed, read-only, version of the game board
     *  to determine if a winning move has been made.
     *
     *  If one has been made, the state of the LiveData variables [_isWin]
     *  and [_winningPlayer] are changed to reflect the results via [announceWinner].
     */
    fun testGameBoardForWin(gameBoard: List<List<String>>) {
        /* Vertical checks for the win:
                 If the set of a specified column does not contain an empty space,
                 and is equal to the set of any single space within it,
                 it must be three in a row */
        // Column 1
        testRow(
            setOf(gameBoard[0][0], gameBoard[1][0], gameBoard[2][0]),
            setOf(gameBoard[0][0])
        )

        // Column 2
        testRow(
            setOf(gameBoard[0][1], gameBoard[1][1], gameBoard[2][1]),
            setOf(gameBoard[1][1])
        )

        // Column 3
        testRow(
            setOf(gameBoard[0][2], gameBoard[1][2], gameBoard[2][2]),
            setOf(gameBoard[2][2])
        )

        /* Horizontal checks for the win:
                 If the set of a specified row does not contain an empty space,
                 and is equal to the set of any single space within it,
                 it must be three in a row */
        // Row 1
        testRow(
            gameBoard[0].toSet(),
            setOf(gameBoard[0][0])
        )

        // Row 2
        testRow(
            gameBoard[1].toSet(),
            setOf(gameBoard[1][1])
        )

        // Row 3
        testRow(
            gameBoard[2].toSet(),
            setOf(gameBoard[2][2])
        )

        /* Diagonal checks for the win:
                 If the set of a specified diagonal row does not contain an empty space,
                 and is equal to the set of any single space within it,
                 it must be three in a row */
        // Top left to bottom right
        testRow(
            setOf(gameBoard[0][0], gameBoard[1][1], gameBoard[2][2]),
            setOf(gameBoard[1][1])
        )

        // Top right to bottom left
        testRow(
            setOf(gameBoard[0][2], gameBoard[1][1], gameBoard[2][0]),
            setOf(gameBoard[1][1])
        )
    }

    /**
     * Tests given row to see if it is three in a row for a player.
     * If it is, it will return true and announce said player.
     *
     * @param row The row being tested for a win
     *
     * @param box A box within that row that can be used to compare sets for a win
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun testRow(row: Set<String>, box: Set<String>): Boolean {
        return if (!(row.contains(""))) {
            if (row == box) {
                // Convert set of single box to list to allow access to contained string
                announceWinner(box.toList()[0])
                return true
            }
            false
        } else {
            false
        }
    }

    /**
     * Retrieves necessary image to place in clicked space.
     *
     * @param boardValue Value of space in game board
     *
     * @return Resource Id for the current player's symbol
     */
    fun getIcon(boardValue: String?): Int {
        return try {
            when (boardValue) {
                "X" -> R.drawable.icon_x
                "O" -> R.drawable.icon_o
                else -> throw IllegalArgumentException("Must pass valid symbol to getIcon")
            }
        } catch (e: IllegalArgumentException) {
            Timber.e(e.message.toString())
            R.drawable.ic_baseline_error_outline_24
        }
    }

    /**
     * Detects if a move is valid. An invalid move is one placed on top of a previously set space.
     *
     * @param boardValue Value of space in game board
     *
     * @return Boolean where true = valid, false = not valid
     */
    private fun moveIsValid(boardValue: String): Boolean = boardValue == ""

    /**
     * Swaps the value of the selected player.
     */
    private fun swapCurrentPlayer() =
        if (_currentPlayer.value == "X") _currentPlayer.value = "O" else _currentPlayer.value = "X"

    /**
     * When box is clicked by the user:
     *   - Checks if the move is valid
     *      - If not: return false
     *      - If so: Updates the game board model with the new
     *        value and switches the player in the viewModel
     *
     * @param x X axis value on the grid
     *
     * @param y Y axis value on the grid
     *
     * @return Boolean where true = valid click, false = not valid click
     */
    fun clickBox(x: Int, y: Int): Boolean {
        return if (moveIsValid(gameBoard.value!![x][y])) {
            GameBoardModelImpl.addSymbol(x, y, currentPlayer.value.toString())
            swapCurrentPlayer()
            true
        } else {
            false
        }
    }

    /**
     * Sets _winningPlayer to "DRAW"
     */
    fun declareDraw() {
        _winningPlayer.value = "DRAW"
    }

    /**
     * Resets the viewModel and GameBoardImpl state
     */
    fun resetGameState() {
        viewModelScope.launch {
            GameBoardModelImpl.resetState()
            _playerOneName.value = ""
            _playerTwoName.value = ""
            _isWin.value = false
            _winningPlayer.value = ""
            _currentPlayer.value = "X"
        }
    }

}