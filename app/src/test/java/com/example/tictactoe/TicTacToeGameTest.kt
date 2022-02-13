package com.example.tictactoe

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.Test

class TicTacToeGameTest {

    @get:Rule() // Ensures liveData doesn't run on main thread
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun test_test() {

    }

}