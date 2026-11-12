package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.repository.SeedData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("GameTactics", appName)
    }

    @Test
    fun `verify seed data contains game tricks and guides`() {
        val tricks = SeedData.initialTricks
        val games = SeedData.initialGames
        assertTrue("Seed tricks should not be empty", tricks.isNotEmpty())
        assertTrue("Contains Fall Guys in initial games", games.any { it.gameId == "fall_guys" })
        assertTrue("Contains Fall Guys tricks", tricks.any { it.gameId == "fall_guys" })
        assertTrue("Contains guide with steps", tricks.all { it.steps.isNotEmpty() })
    }
}
