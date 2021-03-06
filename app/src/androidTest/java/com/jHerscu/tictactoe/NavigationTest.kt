package com.jHerscu.tictactoe

import androidx.fragment.app.Fragment
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.jHerscu.tictactoe.ui.GameFragment
import com.jHerscu.tictactoe.ui.GameFragmentArgs
import com.jHerscu.tictactoe.ui.LandingFragment
import org.hamcrest.CoreMatchers.`is`
import org.junit.Test
import org.junit.runner.RunWith

const val THEME = R.style.Theme_TicTacToe

@RunWith(AndroidJUnit4::class)
@LargeTest
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

    // Extracts initialization of Game fragment to use in multiple (possible) tests in larger app
    private fun gameFragmentInit() {
        // Frame args as safe args object and pass them to init
        val bundle = GameFragmentArgs("X", "O").toBundle()

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

        /* w/ JUnit

        assertEquals(
            "Navigation to GameFragment failed",
            R.id.gameFragment,
            navController.currentDestination?.id
        ) */

        // w/ Hamcrest
        assertThat(
            "Navigation to GameFragment failed",
            navController.currentDestination?.id,
            `is`(R.id.gameFragment)
        )
    }

    @Test
    fun gameToLandingFragmentTest() {

        gameFragmentInit()

        onView(withId(R.id.button_end_game))
            .perform(click())

        /* w/ JUnit

        assertEquals(
            "Navigation to LandingFragment failed",
            R.id.landingFragment,
            navController.currentDestination?.id
        ) */

        // w/ Hamcrest
        assertThat(
            "Navigation to GameFragment failed",
            navController.currentDestination?.id,
            `is`(R.id.landingFragment)
        )
    }
}