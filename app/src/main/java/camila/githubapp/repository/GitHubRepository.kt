package camila.githubapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import camila.githubapp.model.PullRequest
import camila.githubapp.model.Repository
import camila.githubapp.network.GithubAPI
import camila.githubapp.network.repositoryList.RepositoryPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GitHubRepository @Inject constructor(private val api: GithubAPI) {

    fun getRepositoriesList(): Flow<PagingData<Repository>> =
        Pager(PagingConfig(1)) {
            RepositoryPagingSource(api)
        }.flow

    suspend fun getPullRequestList(owner: String, repository: String): List<PullRequest> =
        api.pullRequestList(owner, repository)
}