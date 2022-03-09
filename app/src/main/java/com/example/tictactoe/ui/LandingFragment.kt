package com.example.tictactoe.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.tictactoe.databinding.FragmentLandingBinding

/**
 * [LandingFragment] is the first screen visible to the user.
 * It starts a new game and launches [GameFragment].
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

            githubLink.setOnClickListener {
                launchImplicitIntent()
            }

            playerOneNameEditText.setOnKeyListener { view, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    hideSoftKeyboard(view)
                }
                false
            }

            playerTwoNameEditText.setOnKeyListener { view, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    hideSoftKeyboard(view)
                }
                false
            }
        }
    }

    /**
     * Creates action to be used by [navigateToGame].
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
     * Navigates from [LandingFragment] to [GameFragment].
     */
    private fun navigateToGame() {
        findNavController().navigate(createActionFromSafeArgs())
    }

    /**
     * Takes user to my GitHub page for the app.
     */
    private fun launchImplicitIntent() {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Jherscu/TicTacToe"))
        startActivity(intent)
    }

    /**
     * Hides soft keybord when enter key is pressed
     */
    private fun hideSoftKeyboard(view: View) {
        val imm =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    // Resets binding object
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}