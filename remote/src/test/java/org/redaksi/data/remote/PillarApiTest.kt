package org.redaksi.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@kotlinx.serialization.ExperimentalSerializationApi
internal class PillarApiTest {
    private val MESSAGE_RESPONSE_FAILED = "failed to response."
    private val pillarApi: PillarApi by lazy {
        val contentType = MediaType.get("application/json")
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        retrofit2.Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(PillarApi::class.java)
    }

    @Test
    fun articleDetail() {
        runTest {
            val result = pillarApi.articleDetail(2001)

            assertTrue(::articleDetail.name + MESSAGE_RESPONSE_FAILED, result.isSuccessful)
            assertEquals(result.body()?.title?.rendered, "Tuhan, Ambillah Nyawaku")
        }
    }

    @Test
    fun searchArticle() {
        runTest {
            val result = pillarApi.searchArticle(search = "investasi", page = 1)
            val isNotEmpty = (result.body()?.size ?: 0) > 0

            assertTrue(::searchArticle.name + MESSAGE_RESPONSE_FAILED, result.isSuccessful)
            assertTrue("Articles is empty", isNotEmpty)
        }
    }

    @Test
    fun articlesByCategory() {
        runTest {
            val result = pillarApi.articlesByCategory(RENUNGAN, 1)
            val isNotEmpty = (result.body()?.size ?: 0) > 0

            assertTrue(::articlesByCategory.name + MESSAGE_RESPONSE_FAILED, result.isSuccessful)
            assertTrue("Articles is empty", isNotEmpty)
        }
    }

    @Test
    fun editorChoices() {
        runTest {
            val result = pillarApi.editorChoicesArticles()

            assertTrue(::articleDetail.name + MESSAGE_RESPONSE_FAILED, result.isSuccessful)
            assertEquals(result.body()?.size, 4)
        }
    }

    @Test
    fun newestArticles() {
        runTest {
            val result = pillarApi.newestArticles()

            assertTrue(::articleDetail.name + MESSAGE_RESPONSE_FAILED, result.isSuccessful)
            assertEquals(result.body()?.size, 3)
        }
    }
}
