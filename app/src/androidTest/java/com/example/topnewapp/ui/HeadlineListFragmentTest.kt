package com.example.topnewapp.ui

import com.example.topnewapp.R
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.topnewapp.data.Article
import com.example.topnewapp.ui.list.HeadlineListFragment
import com.example.topnewapp.util.ApiResult
import androidx.fragment.app.testing.FragmentScenario
import com.example.topnewapp.repositories.FakeHeadlineRepository
import com.example.topnewapp.ui.list.HeadlineListViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import org.hamcrest.core.IsNot.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


@RunWith(AndroidJUnit4::class)
class HeadlineListFragmentTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val fragmentScenarioRule = FragmentScenario.launchInContainer(HeadlineListFragment::class.java)

    private lateinit var viewModel: HeadlineListViewModel

    @Before
    fun setup() {
        hiltRule.inject()
        viewModel = HeadlineListViewModel(FakeHeadlineRepository())
    }

    @Test
    fun testFragmentViews() {
        fragmentScenarioRule.onFragment { fragment ->
            onView(withId(R.id.headlineRecyclerView)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun testEmptyState() {
        // Set up a scenario where the repository returns ApiResult.Success with an empty list
        val result = ApiResult.Success(emptyList<Article>())

        // Launch the fragment
        fragmentScenarioRule.onFragment { fragment ->
            // Set the value of topHeadlines
            fragment.viewModel.setTopHeadlines(result)

            // Check if the empty state views are displayed
            onView(withId(R.id.txtMsg)).check(matches(isDisplayed()))
            onView(withId(R.id.headlineRecyclerView)).check(matches(not(isDisplayed())))
        }
    }

    @Test
    fun testErrorState() {
        // Set up a scenario where the result is ApiResult.Error
        val errorResult = ApiResult.Error(Exception("Fake error"))

        // Launch the fragment
        fragmentScenarioRule.onFragment { fragment ->
            // Set the value of topHeadlines
            fragment.viewModel.setTopHeadlines(errorResult)

            // Check if the error state views are displayed
            onView(withId(R.id.txtMsg)).check(matches(isDisplayed()))
            onView(withId(R.id.headlineRecyclerView)).check(matches(not(isDisplayed())))
        }
    }


}

