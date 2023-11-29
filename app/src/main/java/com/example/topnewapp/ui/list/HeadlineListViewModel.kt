package com.example.topnewapp.ui.list

import androidx.lifecycle.ViewModel
import com.example.topnewapp.data.Article
import com.example.topnewapp.repository.HeadlineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import com.example.topnewapp.BuildConfig
import com.example.topnewapp.util.ApiResult
import kotlinx.coroutines.launch

@HiltViewModel
class HeadlineListViewModel @Inject constructor(
    val headlineRepository: HeadlineRepository
) : ViewModel() {

    private val _topHeadlines = MutableStateFlow<ApiResult<List<Article>>>(ApiResult.Loading)
    val topHeadlines: StateFlow<ApiResult<List<Article>>> get() = _topHeadlines

    //Load TopHeadLines
    init {
        loadTopHeadlines()
    }

    //For testing purpose set value of topHeadline
    fun setTopHeadlines(headlines: ApiResult<List<Article>>) {
        _topHeadlines.value = headlines
    }

    //Sort top headlines by Date
    private fun sortArticlesByDate(articles: List<Article>): List<Article> {
        return articles.sortedByDescending { it.publishedAt }
    }


    //Top headline list API request
    fun loadTopHeadlines(sources: String = BuildConfig.HEADLINE_SOURCE) {
        viewModelScope.launch {
            headlineRepository.getTopHeadlines(sources).collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        val sortedArticles = sortArticlesByDate(result.data)
                        _topHeadlines.value = ApiResult.Success(sortedArticles)
                    }

                    is ApiResult.Error -> {
                        _topHeadlines.value = result
                    }

                    is ApiResult.Loading -> {
                        _topHeadlines.value = result
                    }
                }
            }
        }
    }
}