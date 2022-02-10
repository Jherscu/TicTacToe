package com.example.tictactoe

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.tictactoe.ui.GameFragment
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GameFragmentNavigationTest {

    // Declare at top level of NavigationTest so navController is accessible in all tests
    private lateinit var navController: TestNavHostController

    @Before
    fun initialize() {
        // Create a test navController
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        // Create test Fragment to work on
        val gameScenario =
        // Passes app theme as argument to prevent test crashing
            // if it can't decide a theme to use for UI
            launchFragmentInContainer<GameFragment>(themeResId = R.style.Theme_TicTacToe)
        // TODO("Figure out how to pass liveargs to GameFragment")
        gameScenario.onFragment { fragment ->
            // Link navController to its graph
            navController.setGraph(R.navigation.nav_graph)
            // Link fragment to its navController
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @Test
    fun gameToLandingFragment() {
        onView(withId(R.id.button_end_game))
            .perform(click())

        assertEquals("Navigation to LandingFragment Failed",
            navController.currentDestination?.id,
            R.id.landingFragment)
    }
}