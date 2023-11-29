package com.example.topnewapp.data

data class TopHeadLineResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
