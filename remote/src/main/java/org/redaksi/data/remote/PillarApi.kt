package org.redaksi.data.remote

import org.redaksi.data.remote.response.base.Article
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PillarApi {
    companion object {
        const val BASE_URL = "https://buletinpillar.org/wp-json/wp/v2/"

        private const val QUERY_SEARCH = "search"
        private const val QUERY_SEARCH_COLUMNS = "search_columns"
        private const val QUERY_PAGE = "page"
        private const val QUERY_CATEGORIES = "categories"
        private const val QUERY_PILIHAN = "pilihan"
        private const val QUERY_ORDER_BY = "orderby"
        private const val QUERY_PER_PAGE = "per_page"

        const val QUERY_SEARCH_COLUMNS_DEFAULT = "post_title"
        private const val QUERY_PILIHAN_DEFAULT = "yes"
        private const val QUERY_ORDER_BY_DEFAULT = "modified"
    }

    @GET("posts/{id}")
    suspend fun articleDetail(@Path("id") articleId: Int): Response<Article>

    @GET("posts")
    suspend fun searchArticle(
        @Query(QUERY_SEARCH)
        search: String,
        @Query(QUERY_SEARCH_COLUMNS)
        searchColumns: String = QUERY_SEARCH_COLUMNS_DEFAULT,
        @Query(QUERY_PAGE)
        page: Int
    ): Response<List<Article>>

    @GET("posts")
    suspend fun articlesByCategory(
        @CategoryId
        @Query(QUERY_CATEGORIES)
        categories: Int,
        @Query(QUERY_PAGE)
        page: Int
    ): Response<List<Article>>

    @GET("posts")
    suspend fun editorChoicesArticles(
        @Query(QUERY_PILIHAN)
        pilihan: String = QUERY_PILIHAN_DEFAULT,
        @Query(QUERY_ORDER_BY)
        orderBy: String = QUERY_ORDER_BY_DEFAULT,
    ): Response<List<Article>>

    @GET("posts")
    suspend fun newestArticles(
        @Query(QUERY_PER_PAGE)
        perPage: Int = 4
    ): Response<List<Article>>
}
