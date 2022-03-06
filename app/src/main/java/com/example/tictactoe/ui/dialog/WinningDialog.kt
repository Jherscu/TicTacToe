package com.example.tictactoe.ui.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.tictactoe.R
import com.example.tictactoe.model.TicTacToeViewModel
import com.example.tictactoe.ui.GameFragment

class WinningDialog(
    private val viewModel: TicTacToeViewModel,
    private val fragment: GameFragment
) : DialogFragment() {

    private lateinit var listener: WinningDialogListener

    /* The fragment that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    interface WinningDialogListener {
        fun onWinningDialogPositiveClick(
            dialog: DialogFragment,
            viewModel: TicTacToeViewModel,
            fragment: GameFragment
        )

        fun onWinningDialogNegativeClick(
            dialog: DialogFragment,
            viewModel: TicTacToeViewModel
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as WinningDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                (context.toString() +
                        " must implement NoticeDialogListener")
            )
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            with(builder) {
                if (viewModel.winningPlayer.value == "DRAW") {
                    this.setMessage(getString(R.string.draw))
                } else {
                    this.setMessage(getString(R.string.you_win, viewModel.winningPlayer.value))
                }
            }

            builder.setPositiveButton(R.string.play_again) { _, _ ->
                // Send the positive button event back to the host fragment
                listener.onWinningDialogPositiveClick(this, viewModel, fragment)
            }

            builder.setNegativeButton(R.string.exit_game) { _, _ ->
                // Send the positive button event back to the host fragment
                listener.onWinningDialogNegativeClick(this, viewModel)
            }

            builder.create()
        } ?: throw IllegalStateException("Context cannot be null")
    }

}