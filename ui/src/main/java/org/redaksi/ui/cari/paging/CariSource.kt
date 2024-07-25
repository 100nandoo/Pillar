package org.redaksi.ui.cari.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.redaksi.data.remote.PillarApi
import org.redaksi.ui.utama.ArticleUi
import org.redaksi.ui.utama.fromResponse

class CariSource(private val pillarApi: PillarApi, val keyword: String) : PagingSource<Int, ArticleUi>() {
    override fun getRefreshKey(state: PagingState<Int, ArticleUi>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleUi> {
        val nextPage = params.key ?: 1
        val result = runCatching { pillarApi.searchArticle(search = keyword, page = nextPage) }
        val response = result.getOrNull()?.body()
        return when {
            result.isSuccess && response != null -> {
                val articleUis = fromResponse(response)
                LoadResult.Page(
                    data = articleUis,
                    prevKey = if (nextPage == 1) null else nextPage.minus(1),
                    nextKey = if (articleUis.isEmpty()) null else nextPage.plus(1)
                )
            }
            else -> {
                LoadResult.Error(result.exceptionOrNull() ?: Throwable("Error when calling load() in CariSource"))
            }
        }
    }
}
