package com.example.topnewapp.repositories

import com.example.topnewapp.data.Article
import com.example.topnewapp.repository.HeadlineRepository
import com.example.topnewapp.util.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeHeadlineRepository(
    private val articles: List<Article> = emptyList(),
) : HeadlineRepository {

    private var shouldThrowError = false

    fun setShouldThrowError(value: Boolean) {
        shouldThrowError = value
    }


    override suspend fun getTopHeadlines(source: String): Flow<ApiResult<List<Article>>> = flow {
        if (shouldThrowError) {
            emit(ApiResult.Error(error = "Fake error"))
        } else {
            emit(ApiResult.Success(articles))
        }
    }
}
