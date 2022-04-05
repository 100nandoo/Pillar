package org.redaksi.data.remote

import org.redaksi.data.remote.response.AllIssuesResponse
import org.redaksi.data.remote.response.ArticleDetailResponse
import org.redaksi.data.remote.response.GenericArticlesResponse
import org.redaksi.data.remote.response.GenericCommentsResponse
import org.redaksi.data.remote.response.GenericIssueWithArticlesResponse
import org.redaksi.data.remote.response.InsertCommentResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val method = "?method="
interface PillarApi {
    @GET("${method}getArticle")
    suspend fun articleDetail(@Query("article_id") articleId: Int): Response<ArticleDetailResponse>

    @GET("${method}listAllIssues")
    suspend fun issues(): Response<AllIssuesResponse>

    @GET("${method}getLastIssueWithArticles")
    suspend fun lastIssue(): Response<GenericIssueWithArticlesResponse>

    @GET("${method}searchArticles")
    suspend fun searchArticle(@Query("query") keyword: String): Response<GenericArticlesResponse>

    @GET("${method}listArticlesForCategory")
    suspend fun articlesByCategory(@CategoryId @Query("category_id") categoryId: Int): Response<GenericArticlesResponse>

    @GET("${method}listArticlesForIssueNumber")
    suspend fun articlesByIssueNumber(@Query("issueNumber") issueNumber: String): Response<GenericIssueWithArticlesResponse>

    @GET("${method}listCommentsForArticle")
    suspend fun commentByArticle(@Query("article_id") articleId: Int): Response<GenericCommentsResponse>

    @GET("${method}listLatestComments")
    suspend fun latestComments(): Response<GenericCommentsResponse>

    @GET("${method}insertComment")
    suspend fun insertComment(
        @Query("article_id") articleId: Int,
        @Query("senderName") senderName: String,
        @Query("senderCity") senderCity: String,
        @Query("senderEmail") senderEmail: String,
        @Query("body") body: String
    ): Response<InsertCommentResponse>
}