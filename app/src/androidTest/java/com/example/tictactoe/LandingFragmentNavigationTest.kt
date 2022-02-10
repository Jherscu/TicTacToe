package com.example.tictactoe

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.tictactoe.ui.LandingFragment
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NavigationTest {

    // Declare at top level of NavigationTest so navController is accessible in all tests
    private lateinit var navController: TestNavHostController

    @Before
    fun initialize() {
        // Create a test navController
        navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        // Create test Fragment to work on
        val landingScenario =
        // Passes app theme as argument to prevent test crashing
            // if it can't decide a theme to use for UI
            launchFragmentInContainer<LandingFragment>(themeResId = R.style.Theme_TicTacToe)

        landingScenario.onFragment { fragment ->
            // Link navController to its graph
            navController.setGraph(R.navigation.nav_graph)
            // Link fragment to its navController
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
    }

    @Test
    fun landingToGameFragmentTest() {
        // Click button to navigate to GameFragment
        onView(withId(R.id.button_start_game))
            .perform(click())

        assertEquals("Navigation to GameFragment Failed", // Error message
            navController.currentDestination?.id, // Expected
            R.id.gameFragment) // Actual
    }

    @Test
    fun gameToLandingFragmentTestv1() {
        navController.setCurrentDestination(R.id.gameFragment)// DOESN'T WORK!!!
        onView(withId(R.id.button_end_game))
            .perform(click())

        assertEquals("Navigation to LandingFragment failed",
            navController.currentDestination?.id,
            R.id.landingFragment)
    }

    @Test
    fun gameToLandingFragmentTestv2() {
        onView(withId(R.id.button_start_game))
            .perform(click())
        // DOESN'T WORK!!! ALSO WON'T WORK BY DELAYING TO GIVE NEXT STATEMENT TIME TO LOAD!
        onView(withId(R.id.button_end_game))
            .perform(click())

        assertEquals("Navigation to LandingFragment failed",
            navController.currentDestination?.id,
            R.id.landingFragment)
    }
}