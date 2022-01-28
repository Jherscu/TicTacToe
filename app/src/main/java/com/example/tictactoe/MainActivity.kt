package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

/**
 * [MainActivity] hosts the fragments for the Tic Tac Toe app.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}