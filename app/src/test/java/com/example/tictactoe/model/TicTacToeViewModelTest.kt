package com.example.tictactoe.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.tictactoe.R
import com.example.tictactoe.data.source.FakeGameBoardModel
import com.example.tictactoe.util.getOrAwaitValue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TicTacToeViewModelTest {

    @get:Rule // Ensures liveData doesn't run on main thread
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: TicTacToeViewModel
    private lateinit var model: FakeGameBoardModel

    @Before
    fun setup_view_model() {
        viewModel = TicTacToeViewModel()
        model = FakeGameBoardModel()
    }

    @After
    fun clean_up_view_model_and_game_board() {
        viewModel.resetGameState()
        model.resetState()
    }

    @Test
    fun set_top_horizontal_row_test_row_returns_true() {

        model.testWin(0, FakeGameBoardModel.Horizontal())

        assertThat(
            "Testing win did not return true for top row",
            viewModel.testRow(
                model.gameBoard.value[0].toSet(),
                setOf(
                    model.gameBoard.value[0][0]
                )
            ),
            `is`(true)
        )
    }

    @Test
    fun set_middle_horizontal_row_test_row_returns_true() {

        model.testWin(1, FakeGameBoardModel.Horizontal())

        assertThat(
            "Testing win did not return true for middle row",
            viewModel.testRow(
                model.gameBoard.value[1].toSet(),
                setOf(
                    model.gameBoard.value[1][0]
                )
            ),
            `is`(true)
        )
    }

    @Test
    fun set_bottom_horizontal_row_test_row_returns_true() {

        model.testWin(2, FakeGameBoardModel.Horizontal())

        assertThat(
            "Testing win did not return true for bottom row",
            viewModel.testRow(
                model.gameBoard.value[2].toSet(),
                setOf(
                    model.gameBoard.value[2][0]
                )
            ),
            `is`(true)
        )
    }

    @Test
    fun set_left_vertical_column_test_column_returns_true() {

        model.testWin(0, FakeGameBoardModel.Vertical())

        assertThat(
            "Testing win did not return true for left column",
            viewModel.testRow(
                setOf(
                    model.gameBoard.value[0][0],
                    model.gameBoard.value[1][0],
                    model.gameBoard.value[2][0]
                ),
                setOf(
                    model.gameBoard.value[0][0]
                )
            ),
            `is`(true)
        )
    }

    @Test
    fun set_middle_vertical_column_test_column_returns_true() {

        model.testWin(1, FakeGameBoardModel.Vertical())

        assertThat(
            "Testing win did not return true for middle column",
            viewModel.testRow(
                setOf(
                    model.gameBoard.value[0][1],
                    model.gameBoard.value[1][1],
                    model.gameBoard.value[2][1]
                ),
                setOf(
                    model.gameBoard.value[1][1]
                )
            ),
            `is`(true)
        )
    }

    @Test
    fun set_right_vertical_column_test_column_returns_true() {

        model.testWin(2, FakeGameBoardModel.Vertical())

        assertThat(
            "Testing win did not return true for right column",
            viewModel.testRow(
                setOf(
                    model.gameBoard.value[0][2],
                    model.gameBoard.value[1][2],
                    model.gameBoard.value[2][2]
                ),
                setOf(
                    model.gameBoard.value[2][2]
                )
            ),
            `is`(true)
        )
    }

    @Test
    fun set_top_left_to_bottom_right_diagonal_test_diagonal_returns_true() {

        model.testWin(FakeGameBoardModel.TopLeftToBottomRight())

        assertThat(
            "Testing win did not return true for top left to bottom right diagonal",
            viewModel.testRow(
                setOf(
                    model.gameBoard.value[0][0],
                    model.gameBoard.value[1][1],
                    model.gameBoard.value[2][2]
                ),
                setOf(
                    model.gameBoard.value[1][1]
                )
            ),
            `is`(true)
        )
    }

    @Test
    fun set_top_right_to_bottom_left_diagonal_test_diagonal_returns_true() {

        model.testWin(FakeGameBoardModel.TopRightToBottomLeft())

        assertThat(
            "Testing win did not return true for top right to bottom left diagonal",
            viewModel.testRow(
                setOf(
                    model.gameBoard.value[0][2],
                    model.gameBoard.value[1][1],
                    model.gameBoard.value[2][0]
                ),
                setOf(
                    model.gameBoard.value[1][1]
                )
            ),
            `is`(true)
        )
    }

    /*
     * Two asserts are used in this test only to reflect the changed state of the viewModel.
     * For the sake of separating concerns, these asserts are placed in a different test than
     * checking that the row returns true.
     */
    @Test
    fun set_top_left_to_bottom_right_diagonal_test_live_data_values_change() {

        model.testWin(FakeGameBoardModel.TopLeftToBottomRight())

        viewModel.testRow(
            setOf(
                model.gameBoard.value[0][0],
                model.gameBoard.value[1][1],
                model.gameBoard.value[2][2]
            ),
            setOf(
                model.gameBoard.value[1][1]
            )
        )

        assertThat(
            "State of viewModel does not reflect isWin as true",
            viewModel.isWin.getOrAwaitValue(),
            `is`(true)
        )

        assertThat(
            "State of viewModel does not reflect winning player",
            viewModel.winningPlayer.getOrAwaitValue(),
            `is`("X")
        )

    }

    @Test
    fun set_icon_as_x_confirm_returns_x_icon() {
        assertThat(
            """getIcon did not return the x icon for value: "X"""",
            viewModel.getIcon("X"),
            `is`(R.drawable.icon_x)
        )
    }

    @Test
    fun set_icon_as_illegal_arg_confirm_returns_exception_and_error_icon() {
        assertThat(
            "getIcon did not throw an exception for illegal arg or return the error icon",
            viewModel.getIcon("ILLEGAL_ARG"),
            `is`(R.drawable.ic_baseline_error_outline_24)
        )
    }
}