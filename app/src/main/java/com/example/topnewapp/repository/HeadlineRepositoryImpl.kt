import com.example.topnewapp.api.HeadlineApiService
import com.example.topnewapp.data.Article
import com.example.topnewapp.repository.HeadlineRepository
import com.example.topnewapp.util.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

// HeadlineRepositoryImpl.kt
class HeadlineRepositoryImpl @Inject constructor(
    private val apiService: HeadlineApiService
) : HeadlineRepository {

    override suspend fun getTopHeadlines(sources: String): Flow<ApiResult<List<Article>>> = flow {
        try {
            val response = apiService.getTopHeadlines(sources)
            if (response.isSuccessful) {
                emit(ApiResult.Success(response.body()?.articles ?: emptyList()))
            } else {
                emit(ApiResult.Error(Exception(response.message())))
            }
        } catch (e: Exception) {
            emit(ApiResult.Error(e))
        }
    }
}
