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

        viewModel.gameBoard.observe(viewLifecycleOwner) {

            with (it[0][0]) {
                updateView(binding.gridUpperLeft, this)
            }

            with (it[0][1]) {
                updateView(binding.gridUpperCenter, this)
            }

            with (it[0][2]) {
                updateView(binding.gridUpperRight, this)
            }

            with (it[1][0]) {
                updateView(binding.gridCenterLeft, this)
            }

            with (it[1][1]) {
                updateView(binding.gridCenter, this)
            }

            with (it[1][2]) {
                updateView(binding.gridCenterRight, this)
            }

            with (it[2][0]) {
                updateView(binding.gridLowerLeft, this)
            }

            with (it[2][1]) {
                updateView(binding.gridLowerCenter, this)
            }

            with (it[2][2]) {
                updateView(binding.gridLowerRight, this)
            }
        }

        binding.apply {
            buttonEndGame.setOnClickListener {
                navigateToHome()
            }

            gridUpperLeft.setOnClickListener {

                // If the box has already been populated with a value
                // return a warning toast
                viewModel.clickBox(0, 0).toastWhenFalse()

                // Checks game board for win
                viewModel.isThreeInARow()
            }

            gridUpperCenter.setOnClickListener {

                // If the box has already been populated with a value
                // return a warning toast
                viewModel.clickBox(0, 1).toastWhenFalse()

                // Checks game board for win
                viewModel.isThreeInARow()
            }

            gridUpperRight.setOnClickListener {

                // If the box has already been populated with a value
                // return a warning toast
                viewModel.clickBox(0, 2).toastWhenFalse()

                // Checks game board for win
                viewModel.isThreeInARow()
            }

            gridCenterLeft.setOnClickListener {

                // If the box has already been populated with a value
                // return a warning toast
                viewModel.clickBox(1, 0).toastWhenFalse()

                // Checks game board for win
                viewModel.isThreeInARow()
            }

            gridCenter.setOnClickListener {

                // If the box has already been populated with a value
                // return a warning toast
                viewModel.clickBox(1, 1).toastWhenFalse()

                // Checks game board for win
                viewModel.isThreeInARow()
            }

            gridCenterRight.setOnClickListener {

                // If the box has already been populated with a value
                // return a warning toast
                viewModel.clickBox(1, 2).toastWhenFalse()

                // Checks game board for win
                viewModel.isThreeInARow()
            }

            gridLowerLeft.setOnClickListener {

                // If the box has already been populated with a value
                // return a warning toast
                viewModel.clickBox(2, 0).toastWhenFalse()

                // Checks game board for win
                viewModel.isThreeInARow()
            }

            gridLowerCenter.setOnClickListener {

                // If the box has already been populated with a value
                // return a warning toast
                viewModel.clickBox(2, 1).toastWhenFalse()

                // Checks game board for win
                viewModel.isThreeInARow()
            }

            gridLowerRight.setOnClickListener {

                // If the box has already been populated with a value
                // return a warning toast
                viewModel.clickBox(2, 2).toastWhenFalse()

                // Checks game board for win
                viewModel.isThreeInARow()
            }

        }
    }


    private fun Boolean.toastWhenFalse() {
        if (equals(false)) {
            Toast.makeText(
                requireContext(),
                R.string.invalid_move,
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    /**
     *
     */
    private fun updatedContentDescription(view: View, symbol: String): String {
        return getString(
            R.string.content_description_format,
            getString(getStringIdFromView(view)),
            symbol
        )
    }

    /**
     *
     */
    private fun updateView(view: View, symbol: String) {

        // Set image and descriptive content description if the space has been clicked by
        // user and updated in gameBoardModelImpl
        if (symbol != "") {
            view.setBackgroundResource(viewModel.getIcon(symbol))
            view.contentDescription = updatedContentDescription(view, symbol)
        } else { // Set default content description

            view.contentDescription = getString(
                    R.string.content_description_format,
                    getString(getStringIdFromView(view)),
                    getString(R.string.empty)
                )

        }
    }

    /**
     * Navigates from [GameFragment] to [LandingFragment]
     */
    private fun navigateToHome() {
        findNavController().navigate(GameFragmentDirections.actionGameFragmentToLandingFragment())
    }

    /**
     * Correspond view with a string used to identify it in [TicTacToeViewModel.displaySymbol]
     *
     * @param view Valid tic tac toe space represented by an ImageView
     *
     * @return String Id for aforementioned space
     */
    private fun getStringIdFromView(view: View): Int {
        return when (view) {
            binding.gridUpperLeft -> R.string.u_l
            binding.gridUpperCenter -> R.string.u_c
            binding.gridUpperRight -> R.string.u_r
            binding.gridCenterLeft -> R.string.c_l
            binding.gridCenter -> R.string.c
            binding.gridCenterRight -> R.string.c_r
            binding.gridLowerLeft -> R.string.l_l
            binding.gridLowerCenter -> R.string.l_c
            binding.gridLowerRight -> R.string.l_r
            else -> throw IllegalArgumentException("Must be a valid ImageView")
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