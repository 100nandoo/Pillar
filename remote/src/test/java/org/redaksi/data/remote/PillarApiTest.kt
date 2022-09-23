package org.redaksi.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

@kotlinx.serialization.ExperimentalSerializationApi
internal class PillarApiTest {
    val MESSAGE_RESPONSE_FAILED = "failed to response."
    private val pillarApi: PillarApi by lazy {
        val contentType = MediaType.get("application/json")
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
        retrofit2.Retrofit.Builder()
            .baseUrl("https://www.buletinpillar.org/prog/api/app-apis.php/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(PillarApi::class.java)
    }

    @Test
    fun articleDetail() {
        runBlocking {
            val result = pillarApi.articleDetail(2001)

            assertTrue(::articleDetail.name + MESSAGE_RESPONSE_FAILED, result.isSuccessful)
            assertEquals(result.body()?.article?.title, "Iman dan Penglihatan: Kelompok Pertama")
        }
    }

    @Test
    fun searchArticle() {
        runBlocking {
            val result = pillarApi.searchArticle("investasi")
            val isNotEmpty = (result.body()?.size ?: 0) > 0

            assertTrue(::searchArticle.name + MESSAGE_RESPONSE_FAILED, result.isSuccessful)
            assertTrue("Articles is empty", isNotEmpty)
        }
    }

    @Test
    fun articlesByCategory() {
        runBlocking {
            val result = pillarApi.articlesByCategory(RENUNGAN)
            val isNotEmpty = (result.body()?.size ?: 0) > 0

            assertTrue(::articlesByCategory.name + MESSAGE_RESPONSE_FAILED, result.isSuccessful)
            assertTrue("Articles is empty", isNotEmpty)
        }
    }
}
