package com.example.tictactoe

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.example.tictactoe.ui.GameFragment
import com.example.tictactoe.ui.GameFragmentArgs
import com.example.tictactoe.ui.LandingFragment
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

const val THEME = R.style.Theme_TicTacToe

@RunWith(JUnit4::class)
class NavigationTest {

    // Declare navController at top level so it can be accessed from any test in the class
    private lateinit var navController: TestNavHostController

    // Use Generic type with fragment as upper bound to pass any type of FragmentScenario
    private fun <T : Fragment> init(scenario: FragmentScenario<T>) {

        // Create a test navController
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        // Calls on main thread
        scenario.onFragment { fragment ->
            // Link navController to its graph
            navController.setGraph(R.navigation.nav_graph)
            // Link fragment to its navController
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

    }

    // Extracts initialization of Game fragment to use in multiple tests
    private fun gameFragmentInit() {
        // Frame args as safe args object and pass them to init
        val args = GameFragmentArgs("X", "O")
        val bundle = args.toBundle()

        init(
            scenario = launchFragmentInContainer<GameFragment>(
                themeResId = THEME,
                fragmentArgs = bundle
            )
        )

        // Ensure that the NavController is set to the expected destination
        // using the ID from your navigation graph associated with GameFragment
        runOnUiThread {
            // Just like setGraph(), this needs to be called on the main thread
            navController.setCurrentDestination(R.id.gameFragment, bundle)
        }
    }

    @Test
    fun landingToGameFragmentTest() {

        init(launchFragmentInContainer<LandingFragment>(themeResId = THEME))

        // Click button to navigate to GameFragment
        onView(withId(R.id.button_start_game))
            .perform(click())

        assertEquals(
            "Navigation to GameFragment failed",
            R.id.gameFragment,
            navController.currentDestination?.id
        )
    }

    @Test
    fun gameToLandingFragmentTest() {

        gameFragmentInit()

        onView(withId(R.id.button_end_game))
            .perform(click())

        assertEquals(
            "Navigation to LandingFragment failed",
            R.id.landingFragment,
            navController.currentDestination?.id
        )
    }
}