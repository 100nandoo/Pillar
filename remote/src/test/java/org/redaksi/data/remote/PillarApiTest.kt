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
    fun issues() {
        runBlocking {
            val result = pillarApi.issues()
            val isNotEmpty = result.body()?.issues?.items?.size ?: 0 > 0

            assertTrue(::issues.name + MESSAGE_RESPONSE_FAILED, result.isSuccessful)
            assertTrue("Issue is not empty", isNotEmpty)
            assertEquals(
                "Issue `total` equals actual `issues` size",
                result.body()?.issues?.total,
                result.body()?.issues?.items?.size
            )
        }
    }

    @Test
    fun lastIssue() {
        runBlocking {
            val result = pillarApi.lastIssue()
            val isNotEmpty = result.body()?.articles?.items?.size ?: 0 > 0

            assertTrue(::lastIssue.name + MESSAGE_RESPONSE_FAILED, result.isSuccessful)

            assertNotNull("`issueNumber` is null", result.body()?.issue?.issueNumber)
            assertNotNull("`monthDisplay` is null", result.body()?.issue?.monthDisplay)
            assertTrue("List of Article is empty", isNotEmpty)
        }
    }

    @Test
    fun searchArticle() {
        runBlocking {
            val result = pillarApi.searchArticle("investasi", null, null)
            val isNotEmpty = result.body()?.articles?.items?.size ?: 0 > 0

            assertTrue(::searchArticle.name + MESSAGE_RESPONSE_FAILED, result.isSuccessful)
            assertTrue("Articles is empty", isNotEmpty)
        }
    }

    @Test
    fun articlesByCategory() {
        runBlocking {
            val result = pillarApi.articlesByCategory(RENUNGAN)
            val isNotEmpty = result.body()?.articles?.items?.size ?: 0 > 0

            assertTrue(::articlesByCategory.name + MESSAGE_RESPONSE_FAILED, result.isSuccessful)
            assertTrue("Articles is empty", isNotEmpty)
        }
    }

    @Test
    fun articlesByIssueNumber() {
        runBlocking {
            val result = pillarApi.articlesByIssueNumber("223")
            val isNotEmpty = result.body()?.articles?.items?.size ?: 0 > 0

            assertTrue(::articlesByIssueNumber.name + MESSAGE_RESPONSE_FAILED, result.isSuccessful)
            assertTrue("Articles is empty", isNotEmpty)
            assertNotNull("`issueNumber` is null", result.body()?.issue?.issueNumber)
            assertNotNull("`monthDisplay` is null", result.body()?.issue?.monthDisplay)
        }
    }

    @Test
    fun commentByArticle() {
        runBlocking {
            val result = pillarApi.commentByArticle(1008)
            val isNotEmpty = result.body()?.comments?.items?.size ?: 0 > 0

            assertTrue(::commentByArticle.name + MESSAGE_RESPONSE_FAILED, result.isSuccessful)
            assertTrue("Comments is empty", isNotEmpty)
        }
    }

    @Test
    fun latestComments() {
        runBlocking {
            val result = pillarApi.latestComments()
            val isNotEmpty = result.body()?.comments?.items?.size ?: 0 > 0

            assertTrue(::latestComments.name + MESSAGE_RESPONSE_FAILED, result.isSuccessful)
            assertTrue("Comments is empty", isNotEmpty)
        }
    }

    @Test
    fun insertComment() {
        runBlocking {
            // as yukuku@gmail.com requested, article id for testing MUST use value `3`
            val result =
                pillarApi.insertComment(3, "JUNIT 4", "Java", "100nandoo@gmail.com", "Bagus")

            assertTrue(::insertComment.name + MESSAGE_RESPONSE_FAILED, result.isSuccessful)
            assertTrue("Comment id is empty", result.body()?.commentId ?: 0 > 0)
            assertTrue("Comment is not ok", result.body()?.ok ?: false)
        }
    }
}
