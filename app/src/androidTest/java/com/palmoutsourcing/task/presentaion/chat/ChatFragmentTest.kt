package com.palmoutsourcing.task.presentaion.chat

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChatFragmentTest {
    @Test
    fun testViewModelObserversRemovedAfterDestroy() {
        var viewModel: ChatViewModel? = null
        val scenario = launchFragmentInContainer<ChatFragment>()

        // Move fragment to STARTED state to trigger onViewCreated
        scenario.moveToState(Lifecycle.State.STARTED)

        // Get reference to the ViewModel and verify observers are present
        scenario.onFragment { fragment ->
            viewModel = fragment.getViewModelForTesting()

            // Verify that observers are present when fragment is active
            assertTrue(
                "ViewModel should have observers when fragment is active",
                viewModel?.messages?.hasObservers() == true
            )
        }

        // Move fragment to DESTROYED state
        scenario.moveToState(Lifecycle.State.DESTROYED)

        // Verify that observers are removed after destroy
        assertTrue(
            "ViewModel should not have observers after fragment is destroyed",
            viewModel?.messages?.hasObservers() == false
        )
    }
}