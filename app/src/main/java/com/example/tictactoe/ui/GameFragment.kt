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
import com.example.tictactoe.databinding.FragmentGameBinding
import com.example.tictactoe.model.TicTacToeViewModel
import com.google.android.material.snackbar.BaseTransientBottomBar
import kotlin.time.Duration


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

        binding.apply {
            buttonEndGame.setOnClickListener {
                navigateToHome()
            }
        }

        Toast.makeText(this.context, viewModel.playerOneName.value, Toast.LENGTH_SHORT).show()
        Toast.makeText(this.context, viewModel.playerTwoName.value, Toast.LENGTH_LONG).show()
    }

    /**
     * Navigates from [GameFragment] to [LandingFragment]
     */
    private fun navigateToHome() {
        findNavController().navigate(GameFragmentDirections.actionGameFragmentToLandingFragment())
    }

    // Resets binding object
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}