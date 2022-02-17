package com.example.tictactoe.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow


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

    private val _isWin = MutableLiveData(false)
    val isWin: LiveData<Boolean> = _isWin

    private val _winningPlayer = MutableLiveData<String>()
    val winningPlayer: LiveData<String> = _winningPlayer

    init {
        GameBoardModelImpl.resetState()
    }

    /**
     * Updates viewModel properties with safeArgs name input from
     * [com.example.tictactoe.ui.LandingFragment]
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
            "X" -> _winningPlayer.value = _playerOneName.value
            "O" -> _winningPlayer.value = _playerTwoName.value
        }
        _isWin.value = true
    }

    /**
     *  Reads the publicly exposed, read-only, version of the game board
     *  to determine if a winning move has been made.
     *
     *  If one has been made, the state of the LiveData variables [_isWin]
     *  and [_winningPlayer] are changed to reflect the results via [announceWinner]
     */
    fun isThreeInARow(gameBoard: StateFlow<List<List<String>>>): Boolean {
        return when {

            /* Vertical checks for the win:
                If the set of a specified column does not contain an empty space,
                and is equal to the set of any single space within it,
                it must be three in a row */
            // Column 1
            !(GameBoardPart.COLUMN_LEFT.part.contains("")) -> {
                if (GameBoardPart.COLUMN_LEFT.part == GameBoardPart.BOX_TOP_LEFT.part) {
                    announceWinner(GameBoardPart.BOX_TOP_LEFT.part.toString())
                    return true
                }
                false
            }

            // Column 2
            !(GameBoardPart.COLUMN_MIDDLE.part.contains("")) -> {
                if (GameBoardPart.COLUMN_MIDDLE.part == GameBoardPart.BOX_CENTER.part) {
                    announceWinner(GameBoardPart.BOX_CENTER.part.toString())
                    return true
                }
                false
            }

            // Column 3
            !(GameBoardPart.COLUMN_RIGHT.part.contains("")) -> {
                if (GameBoardPart.COLUMN_RIGHT.part == GameBoardPart.BOX_BOTTOM_RIGHT.part) {
                    announceWinner(GameBoardPart.BOX_BOTTOM_RIGHT.part.toString())
                    return true
                }
                false
            }

            /* Horizontal checks for the win:
                If the set of a specified row does not contain an empty space,
                and is equal to the set of any single space within it,
                it must be three in a row */
            // Row 1
            !(GameBoardPart.ROW_TOP.part.contains("")) -> {
                if (GameBoardPart.ROW_TOP.part == GameBoardPart.BOX_TOP_LEFT.part) {
                    announceWinner(GameBoardPart.BOX_TOP_LEFT.part.toString())
                    return true
                }
                false
            }

            // Row 2
            !(GameBoardPart.ROW_MIDDLE.part.contains("")) -> {
                if (GameBoardPart.ROW_MIDDLE.part == GameBoardPart.BOX_CENTER.part) {
                    announceWinner(GameBoardPart.BOX_CENTER.part.toString())
                    return true
                }
                false
            }

            // Row 3
            !(GameBoardPart.ROW_BOTTOM.part.contains("")) -> {
                if (GameBoardPart.ROW_BOTTOM.part == setOf(GameBoardPart.BOX_BOTTOM_RIGHT.part)) {
                    announceWinner(GameBoardPart.BOX_BOTTOM_RIGHT.part.toString())
                    return true
                }
                false
            }

            /* Diagonal checks for the win:
                If the set of a specified diagonal row does not contain an empty space,
                and is equal to the set of any single space within it,
                it must be three in a row */
            // Top left to bottom right
            !(GameBoardPart.DIAGONAL_TOP_LEFT_TO_BOTTOM_RIGHT.part.contains("")) -> {
                if (GameBoardPart.DIAGONAL_TOP_LEFT_TO_BOTTOM_RIGHT.part == GameBoardPart.BOX_CENTER.part) {
                    announceWinner(GameBoardPart.BOX_CENTER.part.toString())
                    return true
                }
                false
            }

            // Top right to bottom left
            !(GameBoardPart.DIAGONAL_TOP_RIGHT_TO_BOTTOM_LEFT.part.contains("")) -> {
                if (GameBoardPart.DIAGONAL_TOP_RIGHT_TO_BOTTOM_LEFT.part == GameBoardPart.BOX_CENTER.part) {
                    announceWinner(GameBoardPart.BOX_CENTER.part.toString())
                    return true
                }
                false
            }
            else -> false
        }
    }

}