package com.example.tictactoe.data.source


import com.example.tictactoe.data.source.FakeGameBoardModel.gameBoard

enum class FakeGameBoardPart(val part: Set<String>) {
    COLUMN_LEFT(setOf(gameBoard.value[0][0], gameBoard.value[1][0], gameBoard.value[2][0])),
    COLUMN_MIDDLE(setOf(gameBoard.value[0][1], gameBoard.value[1][1], gameBoard.value[2][1])),
    COLUMN_RIGHT(setOf(gameBoard.value[0][2], gameBoard.value[1][2], gameBoard.value[2][2])),
    ROW_TOP(gameBoard.value[0].toSet()),
    ROW_MIDDLE(gameBoard.value[1].toSet()),
    ROW_BOTTOM(gameBoard.value[2].toSet()),
    DIAGONAL_TOP_LEFT_TO_BOTTOM_RIGHT(
        setOf(
            gameBoard.value[0][0],
            gameBoard.value[1][1],
            gameBoard.value[2][2]
        )
    ),
    DIAGONAL_TOP_RIGHT_TO_BOTTOM_LEFT(
        setOf(
            gameBoard.value[0][2],
            gameBoard.value[1][1],
            gameBoard.value[2][0]
        )
    ),
    BOX_TOP_LEFT(setOf(gameBoard.value[0][0])),
    BOX_CENTER(setOf(gameBoard.value[1][1])),
    BOX_BOTTOM_RIGHT(setOf(gameBoard.value[2][2])),
}