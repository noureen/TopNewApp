package com.example.topnewapp.api

import com.example.topnewapp.BuildConfig
import com.example.topnewapp.data.TopHeadLineResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HeadlineApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<TopHeadLineResponse>
}