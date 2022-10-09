package org.redaksi.data.remote

import org.redaksi.data.remote.response.base.Article
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val baseUrl = "https://buletinpillar.org/wp-json/wp/v2/"

interface PillarApi {
    @GET("posts/{id}")
    suspend fun articleDetail(@Path("id") articleId: Int): Response<Article>

    @GET("posts")
    suspend fun searchArticle(
        @Query("search")
        search: String
    ): Response<List<Article>>

    @GET("posts")
    suspend fun articlesByCategory(
        @CategoryId @Query("categories")
        categories: Int,
        @Query("page")
        page: Int
    ): Response<List<Article>>

    @GET("posts")
    suspend fun editorChoicesArticles(
        @Query("pilihan")
        pilihan: String = "yes"
    ): Response<List<Article>>

    @GET("posts")
    suspend fun newestArticles(
        @Query("per_page")
        perPage: Int = 3
    ): Response<List<Article>>
}
