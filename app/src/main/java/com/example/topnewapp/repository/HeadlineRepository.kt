package com.example.topnewapp.repository

import com.example.topnewapp.data.Article
import com.example.topnewapp.util.ApiResult
import kotlinx.coroutines.flow.Flow


interface HeadlineRepository {
    suspend fun getTopHeadlines(source: String): Flow<ApiResult<List<Article>>>
}
