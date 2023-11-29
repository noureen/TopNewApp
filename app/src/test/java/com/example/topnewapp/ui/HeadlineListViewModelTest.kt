package com.example.topnewapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.topnewapp.MainCoroutineRule
import com.example.topnewapp.data.Article
import com.example.topnewapp.repositories.FakeHeadlineRepository
import com.example.topnewapp.ui.list.HeadlineListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.topnewapp.getOrAwaitValueTest
import com.example.topnewapp.util.ApiResult
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HeadlineListViewModelTest {

    // Mocking the repository and coroutine rule for testing suspending functions
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineRule = MainCoroutineRule()


    private lateinit var viewModel: HeadlineListViewModel

    @Before
    fun setup() {
        viewModel = HeadlineListViewModel(FakeHeadlineRepository())
    }

    @Test
    fun `Get top article with, returns success`() /*= runBlockingTest*/ {
        viewModel.loadTopHeadlines()
        val value = viewModel.topHeadlines.getOrAwaitValueTest()
        assertEquals(ApiResult.Success(emptyList<Article>()), value)
    }


    @Test
    fun `Get top articles, returns error`() /*= runTest*/ {
        // Arrange: Set the repository to return an error
        val fakeRepository = (viewModel.headlineRepository as FakeHeadlineRepository)
        fakeRepository.setShouldThrowError(true)

        // Act: Load top headlines
        viewModel.loadTopHeadlines()

        // Assert: Check that the result is an error
        val value = viewModel.topHeadlines.getOrAwaitValueTest()
        val expectedErrorMessage = "Fake error"
        assertEquals(ApiResult.Error(error = expectedErrorMessage), value)
    }
}
