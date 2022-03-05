package com.example.tictactoe

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.tictactoe.ui.GameFragment
import com.example.tictactoe.ui.GameFragmentArgs
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class GameFragmentTest {

    @Before
    fun setup_game_fragment() {
        launchFragmentInContainer<GameFragment>(
            themeResId = THEME,
            fragmentArgs = GameFragmentArgs("X", "O").toBundle()
        )
    }

    @Test
    fun do_nothing_confirm_initial_content_description() {
        onView(withId(R.id.grid_center))
            .check(matches(withContentDescription("Center Box: Empty")))
    }

    @Test
    fun set_symbol_confirm_content_description_changes() {
        onView(withId(R.id.grid_center))
            .perform(click())

        onView(withId(R.id.grid_center))
            .check(matches(withContentDescription("Center Box: X")))
    }
}