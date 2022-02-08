package com.example.tictactoe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * [MainActivity] hosts the fragments for the Tic Tac Toe app.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}