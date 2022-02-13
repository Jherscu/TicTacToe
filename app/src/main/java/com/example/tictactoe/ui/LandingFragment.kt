package com.example.tictactoe.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.tictactoe.databinding.FragmentLandingBinding

/**
 * [LandingFragment] is the first screen visible to the user.
 * It starts a new game and launches [GameFragment]
 */
class LandingFragment : Fragment() {

    // Declare outside of onCreateView() so binding can be accessed throughout
    // the whole fragment
    private var _binding: FragmentLandingBinding? = null

    // This property is only safe to call between onCreateView() and onDestroyView()
    private val binding: FragmentLandingBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLandingBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonStartGame.setOnClickListener {
                navigateToGame()
            }
        }
    }

    /**
     * Creates action to be used by [navigateToGame]
     *
     * @return [LandingFragmentDirections.actionLandingFragmentToGameFragment]
     */
    private fun createActionFromSafeArgs(): NavDirections {
        // Passes contents of name edit texts as names
        // If the names haven't been filled out, it returns default values
        return LandingFragmentDirections.actionLandingFragmentToGameFragment(
            binding.playerOneNameEditText.text.toString().ifEmpty { "X" },
            binding.playerTwoNameEditText.text.toString().ifEmpty { "O" })
    }

    /**
     * Navigates from [LandingFragment] to [GameFragment]
     */
    private fun navigateToGame() {
        findNavController().navigate(createActionFromSafeArgs())
    }

    // Resets binding object
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}