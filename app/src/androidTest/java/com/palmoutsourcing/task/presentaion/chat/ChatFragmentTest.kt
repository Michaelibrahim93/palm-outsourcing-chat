package com.palmoutsourcing.task.presentaion.chat

import android.util.Log
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.palmoutsourcing.task.domain.models.Message
import com.palmoutsourcing.task.domain.models.Messages
import com.palmoutsourcing.task.domain.repository.MessagesRepository
import kotlinx.coroutines.flow.flow
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations


@RunWith(AndroidJUnit4::class)
class ChatFragmentTest {
    @Captor
    private lateinit var captor: ArgumentCaptor<Messages?>

    private val testMessages = listOf(
        Message("1", "2", false),
        Message("2", "3", false),
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun chatFragment_whenMessagesEmitted_observerReceivesThem() {
        val repo: MessagesRepository = mock()
        val observer: Observer<Messages?> = mock()
        val factory = TestViewModelFactory(repo)

        `when`(repo.listenToChatMessages()).thenReturn(
            flow { emit(testMessages) }
        )
        doNothing().`when`(observer).onChanged(testMessages)


        val scenario = launchFragmentInContainer<ChatFragment> {
            ChatFragment().apply {
                setFactory(factory)
            }
        }
        scenario.moveToState(Lifecycle.State.STARTED)

        scenario.onFragment { fragment ->
            val viewModel = fragment.getViewModelForTesting()
            viewModel.messages.observe(fragment.viewLifecycleOwner, observer)
        }
        scenario.moveToState(Lifecycle.State.DESTROYED)

        verify(observer).onChanged(captor.capture())

        assertTrue(captor.value == testMessages)
        scenario.close()
    }

    @Test
    fun chatFragment_whenMessagesNotEmitted_observerIsNotCalled() {
        val repo: MessagesRepository = mock()
        val observer: Observer<Messages?> = mock()
        val factory = TestViewModelFactory(repo)

        `when`(repo.listenToChatMessages()).thenReturn(
            flow {  }
        )
        doNothing().`when`(observer).onChanged(testMessages)


        val scenario = launchFragmentInContainer<ChatFragment> {
            ChatFragment().apply {
                setFactory(factory)
            }
        }
        scenario.moveToState(Lifecycle.State.STARTED)

        scenario.onFragment { fragment ->
            val viewModel = fragment.getViewModelForTesting()
            viewModel.messages.observe(fragment.viewLifecycleOwner, observer)
        }
        scenario.moveToState(Lifecycle.State.DESTROYED)

        verify(observer, times(0)).onChanged(testMessages)

        scenario.close()
    }

    @Test
    fun chatFragment_whenFragmentIsDestroyed_viewModelHasNoObservers() {
        lateinit var viewModel: ChatViewModel
        val scenario = launchFragmentInContainer<ChatFragment>()

        scenario.moveToState(Lifecycle.State.STARTED)

        scenario.onFragment { fragment ->
            viewModel = fragment.getViewModelForTesting()

            assertTrue(
                "ViewModel should have observers when fragment is active",
                viewModel.messages.hasObservers()
            )
        }

        scenario.moveToState(Lifecycle.State.DESTROYED)

        assertTrue(
            "ViewModel should not have observers after fragment is destroyed",
            !viewModel.messages.hasObservers()
        )

        scenario.close()
    }
}

class TestViewModelFactory(
    private val fakeRepo: MessagesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Log.d("captor", "modelClass: $modelClass")
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(fakeRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}