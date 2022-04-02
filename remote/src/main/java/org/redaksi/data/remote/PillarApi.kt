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

interface PillarApi {
    @GET("getArticle")
    suspend fun articleDetail(@Query("article_id") articleId: Int): Response<ArticleDetailResponse>

    @GET("listAllIssues")
    suspend fun issues(): Response<AllIssuesResponse>

    @GET("getLastIssueWithArticles")
    suspend fun lastIssue(): Response<GenericIssueWithArticlesResponse>

    @GET("searchArticles")
    suspend fun searchArticle(@Query("query") keyword: String): Response<GenericArticlesResponse>

    @GET("listArticlesForCategory")
    suspend fun articlesByCategory(@CategoryId @Query("category_id") categoryId: Int): Response<GenericArticlesResponse>

    @GET("listArticlesForIssueNumber")
    suspend fun articlesByIssueNumber(@Query("issueNumber") issueNumber: String): Response<GenericIssueWithArticlesResponse>

    @GET("listCommentsForArticle")
    suspend fun commentByArticle(@Query("article_id") articleId: Int): Response<GenericCommentsResponse>

    @GET("listLatestComments")
    suspend fun latestComments(): Response<GenericCommentsResponse>

    @GET("insertComment")
    suspend fun insertComment(
        @Query("article_id") articleId: Int,
        @Query("senderName") senderName: String,
        @Query("senderCity") senderCity: String,
        @Query("senderEmail") senderEmail: String,
        @Query("body") body: String
    ): Response<InsertCommentResponse>
}