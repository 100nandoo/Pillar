package org.redaksi.data.remote

import org.redaksi.data.remote.response.AllIssuesResponse
import org.redaksi.data.remote.response.ArticleDetailResponse
import org.redaksi.data.remote.response.GenericCommentsResponse
import org.redaksi.data.remote.response.GenericIssueWithArticlesResponse
import org.redaksi.data.remote.response.InsertCommentResponse
import org.redaksi.data.remote.response.base.NewArticle
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

const val baseUrl = "https://buletinpillar.org/wp-json/wp/v2/"

interface PillarApi {
    @GET("getArticle")
    suspend fun articleDetail(@Query("article_id") articleId: Int): Response<ArticleDetailResponse>

    @GET("listAllIssues")
    suspend fun issues(): Response<AllIssuesResponse>

    @GET("getLastIssueWithArticles")
    suspend fun lastIssue(): Response<GenericIssueWithArticlesResponse>

    @GET("posts")
    suspend fun searchArticle(
        @Query("search")
        search: String
    ): Response<List<NewArticle>>

    @GET("posts")
    suspend fun articlesByCategory(
        @CategoryId @Query("categories")
        categories: Int
    ): Response<List<NewArticle>>

    @GET("posts")
    suspend fun editorChoicesArticles(
        @Query("pilihan")
        pilihan: String = "yes"
    ): Response<List<NewArticle>>

    @GET("posts")
    suspend fun newestArticles(
        @Query("sticky")
        sticky: String = "true"
    ): Response<List<NewArticle>>

    @GET("listArticlesForIssueNumber")
    suspend fun articlesByIssueNumber(@Query("issueNumber") issueNumber: String): Response<GenericIssueWithArticlesResponse>

    @GET("listCommentsForArticle")
    suspend fun commentByArticle(@Query("article_id") articleId: Int): Response<GenericCommentsResponse>

    @GET("listLatestComments")
    suspend fun latestComments(): Response<GenericCommentsResponse>

    @POST("insertComment")
    suspend fun insertComment(
        @Query("article_id") articleId: Int,
        @Query("senderName") senderName: String,
        @Query("senderCity") senderCity: String,
        @Query("senderEmail") senderEmail: String,
        @Query("body") body: String
    ): Response<InsertCommentResponse>
}
