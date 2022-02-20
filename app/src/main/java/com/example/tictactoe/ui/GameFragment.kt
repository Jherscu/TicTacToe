package com.example.tictactoe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tictactoe.R
import com.example.tictactoe.databinding.FragmentGameBinding
import com.example.tictactoe.model.GameBoardModelImpl
import com.example.tictactoe.model.TicTacToeViewModel


/**
 * [GameFragment] is the fragment where the Tic Tac Toe game takes place.
 * Launches from [LandingFragment]
 */
class GameFragment : Fragment() {

    private val viewModel: TicTacToeViewModel by viewModels()

    // Create arguments object to access args passed from previous fragment
    private val args: GameFragmentArgs by navArgs()

    // Declare outside of onCreateView() so binding can be accessed throughout
    // the whole fragment
    private var _binding: FragmentGameBinding? = null

    // This property is only safe to call between onCreateView() and onDestroyView()
    private val binding get() = _binding!!

    private val gameBoard = GameBoardModelImpl.gameBoard.value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Updates viewModel with player arguments from LandingFragment
        viewModel.updatePlayerName(1, args.playerOneName)
        viewModel.updatePlayerName(2, args.playerTwoName)

        binding.apply {
            buttonEndGame.setOnClickListener {
                navigateToHome()
            }

            gridUpperLeft.setOnClickListener {
                clickBox(0, 0, it)
            }

            gridUpperCenter.setOnClickListener {
                clickBox(0, 1, it)
            }

            gridUpperRight.setOnClickListener {
                clickBox(0, 2, it)
            }

            gridCenterLeft.setOnClickListener {
                clickBox(1, 0, it)
            }

            gridCenter.setOnClickListener {
                clickBox(1, 1, it)
            }

            gridCenterRight.setOnClickListener {
                clickBox(1, 2, it)
            }

            gridLowerLeft.setOnClickListener {
                clickBox(2, 0, it)
            }

            gridLowerCenter.setOnClickListener {
                clickBox(2, 1, it)
            }

            gridLowerRight.setOnClickListener {
                clickBox(2, 2, it)
            }
        }
    }

    /**
     * Navigates from [GameFragment] to [LandingFragment]
     */
    private fun navigateToHome() {
        findNavController().navigate(GameFragmentDirections.actionGameFragmentToLandingFragment())
    }

    /**
     * When box is clicked by the user:
     *   - Checks if the move is valid
     *      - If not: A warning Toast is made
     *      - If so: Updates the game board model and UI with the new
     *        value and switches the player in the viewModel
     *
     * @param x X axis value on the grid
     *
     * @param y Y axis value on the grid
     *
     * @param view The associated view being clicked on by the player
     */
    private fun clickBox(x: Int, y: Int, view: View) {
        viewModel.run {
            if (this.moveIsValid(gameBoard[x][y])) {

                this.displaySymbol(x, y, view, getString(R.string.u_l)) // make string dynamic

                this.swapCurrentPlayer()

                // Checks game board for win
                this.isThreeInARow()
            } else {
                Toast.makeText(
                    this@GameFragment.context,
                    R.string.invalid_move,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    // TODO( If viewModel.isWin == true, create dialog. Announce winning player.
    //  opt 1 end game return to home, opt 2 reset state start over )

    // Resets binding object
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}