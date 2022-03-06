package com.example.tictactoe.ui

import android.os.Bundle
import android.util.Log
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
import com.example.tictactoe.ui.dialog.WinningDialog

const val TAG = "GAME_FRAGMENT"

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

        observeGameBoard() // Used for initial construction/reconstruction of Fragment

        viewModel.isWin.observe(viewLifecycleOwner) {
            if (it) {
                WinningDialog(viewModel, this).show(
                    childFragmentManager,
                    "WinningDialogFragmentAsWin"
                )
            }
        }

        viewModel.currentPlayer.observe(viewLifecycleOwner) {
            binding.playerTurnView.text = when (it) {
                "X" -> getString(R.string.player_turn, viewModel.playerOneName.value)
                "O" -> getString(R.string.player_turn, viewModel.playerTwoName.value)
                else -> getString(R.string.confused_turn)
            }
        }

        viewModel.winningPlayer.observe(viewLifecycleOwner) {
            if (it == "DRAW") {
                WinningDialog(viewModel, this).show(
                    childFragmentManager,
                    "WinningDialogFragmentAsDraw"
                )
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
                // Observe state change and update views
                observeGameBoard()
            }

            gridUpperCenter.setOnClickListener {

                // If the box has already been populated with a value
                // return a warning toast
                viewModel.clickBox(0, 1).toastWhenFalse()
                // Observe state change and update views
                observeGameBoard()
            }

            gridUpperRight.setOnClickListener {

                // If the box has already been populated with a value
                // return a warning toast
                viewModel.clickBox(0, 2).toastWhenFalse()
                // Observe state change and update views
                observeGameBoard()
            }

            gridCenterLeft.setOnClickListener {

                // If the box has already been populated with a value
                // return a warning toast
                viewModel.clickBox(1, 0).toastWhenFalse()
                // Observe state change and update views
                observeGameBoard()
            }

            gridCenter.setOnClickListener {

                // If the box has already been populated with a value
                // return a warning toast
                viewModel.clickBox(1, 1).toastWhenFalse()
                // Observe state change and update views
                observeGameBoard()
            }

            gridCenterRight.setOnClickListener {

                // If the box has already been populated with a value
                // return a warning toast
                viewModel.clickBox(1, 2).toastWhenFalse()
                // Observe state change and update views
                observeGameBoard()
            }

            gridLowerLeft.setOnClickListener {

                // If the box has already been populated with a value
                // return a warning toast
                viewModel.clickBox(2, 0).toastWhenFalse()
                // Observe state change and update views
                observeGameBoard()
            }

            gridLowerCenter.setOnClickListener {

                // If the box has already been populated with a value
                // return a warning toast
                viewModel.clickBox(2, 1).toastWhenFalse()
                // Observe state change and update views
                observeGameBoard()
            }

            gridLowerRight.setOnClickListener {

                // If the box has already been populated with a value
                // return a warning toast
                viewModel.clickBox(2, 2).toastWhenFalse()
                // Observe state change and update views
                observeGameBoard()
            }

        }
    }

    /**
     * Observe the state of the game board and update necessary views when they are acted upon.
     */
    private fun observeGameBoard() {
        viewModel.gameBoard.observe(viewLifecycleOwner) {

            with(it[0][0]) {
                updateView(binding.gridUpperLeft, this)
            }

            with(it[0][1]) {
                updateView(binding.gridUpperCenter, this)
            }

            with(it[0][2]) {
                updateView(binding.gridUpperRight, this)
            }

            with(it[1][0]) {
                updateView(binding.gridCenterLeft, this)
            }

            with(it[1][1]) {
                updateView(binding.gridCenter, this)
            }

            with(it[1][2]) {
                updateView(binding.gridCenterRight, this)
            }

            with(it[2][0]) {
                updateView(binding.gridLowerLeft, this)
            }

            with(it[2][1]) {
                updateView(binding.gridLowerCenter, this)
            }

            with(it[2][2]) {
                updateView(binding.gridLowerRight, this)
            }

            viewModel.testGameBoardForWin(it)

            if (!it.flatten().toSet().contains("")) {
                viewModel.declareDraw()
            }
        }
    }


    /**
     * Extension function that returns a warning toast for false values.
     */
    private fun Boolean.toastWhenFalse() {
        if (equals(false)) {
            Toast.makeText(
                requireActivity().applicationContext,
                R.string.invalid_move,
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    /**
     * Returns content description for a game board space reflecting which symbol it contains.
     *
     * @param view The space on the game board
     *
     * @param symbol Either "X" or "O" depending on the player
     */
    private fun updatedContentDescription(view: View, symbol: String): String {
        return getString(
            R.string.content_description_format,
            getString(getStringIdFromView(view)),
            symbol
        )
    }

    /**
     * Set the image and content description for the selected view.
     *
     * @param view The space on the game board
     *
     * @param symbol Either "X" or "O" depending on the player
     */
    private fun updateView(view: View, symbol: String) {

        view.contentDescription = if (symbol != "") {
            // Also updates background image before assigning description
            view.setBackgroundResource(viewModel.getIcon(symbol))

            updatedContentDescription(view, symbol)
        } else { // Set default description
            getString(
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
     * Correspond view with an identifying string used in the content description builder.
     *
     * @param view Valid game board space represented by an ImageView
     *
     * @return String Id for aforementioned space
     */
    private fun getStringIdFromView(view: View): Int {
        return try {
            when (view) {
                binding.gridUpperLeft -> R.string.u_l
                binding.gridUpperCenter -> R.string.u_c
                binding.gridUpperRight -> R.string.u_r
                binding.gridCenterLeft -> R.string.c_l
                binding.gridCenter -> R.string.c
                binding.gridCenterRight -> R.string.c_r
                binding.gridLowerLeft -> R.string.l_l
                binding.gridLowerCenter -> R.string.l_c
                binding.gridLowerRight -> R.string.l_r
                else -> throw IllegalArgumentException("Must be a valid ImageView associated with a tic tac toe box")
            }
        } catch (e: java.lang.IllegalArgumentException) {
            Log.e(TAG, e.message.toString())
            R.string.invalid_box
        }
    }

    // Resets binding object
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}