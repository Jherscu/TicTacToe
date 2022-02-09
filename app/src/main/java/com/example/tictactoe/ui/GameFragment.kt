package com.example.tictactoe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tictactoe.databinding.FragmentGameBinding

/**
 * [GameFragment] is the fragment where the Tic Tac Toe game takes place.
 * Launches from [LandingFragment]
 */
class GameFragment : Fragment() {

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
        binding.apply {
            buttonEndGame.setOnClickListener {
                navigateToHome()
            }
        }
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