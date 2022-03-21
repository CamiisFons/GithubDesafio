package camila.githubapp.network.repositoryList

import androidx.paging.PagingSource
import androidx.paging.PagingState
import camila.githubapp.model.Repository
import camila.githubapp.network.GithubAPI


class RepositoryPagingSource(private val service: GithubAPI) : PagingSource<Int, Repository>() {
    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {

        return state.anchorPosition

    }
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
            val pageNumber = params.key ?: FIRST_PAGE_INDEX
            return try {
                val response = service.getRepositories(pageNumber)
                var nextPageNumber: Int? = null
                if (!response.items.isNullOrEmpty()) {
                    nextPageNumber = pageNumber + 1
                }
                LoadResult.Page(
                    data = response.items,
                    prevKey = null,
                    nextKey = nextPageNumber
                )
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }

    companion object{
        private const val FIRST_PAGE_INDEX = 1
    }

}


//override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {

//        return try {
//            val currentPage = params.key ?: GITHUB_STARTING_PAGE_INDEX
//            val response = service.getRepositories(currentPage)
//            val data = response.body()?.items ?: emptyList()
//            val responseData = mutableListOf<Repository>()
//            responseData.addAll(data)
//
//            LoadResult.Page(
//                data = responseData,
//                prevKey = if (currentPage == GITHUB_STARTING_PAGE_INDEX) null else - 1,
//                nextKey = currentPage.plus(GITHUB_STARTING_PAGE_INDEX),
//            )
//
//        } catch (exception: IOException) {
//            return LoadResult.Error(exception)
//        } catch (exception: HttpException) {
//            return LoadResult.Error(exception)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }
//    }

//}