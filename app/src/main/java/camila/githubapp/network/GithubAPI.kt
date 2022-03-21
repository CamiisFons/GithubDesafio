package camila.githubapp.network

import camila.githubapp.model.PullRequest
import camila.githubapp.model.RepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPI {

    @GET("/search/repositories?q=language:Java&sort=stars")
    suspend fun getRepositories(
        @Query("page") page: Int,
    ): RepositoryResponse

    @GET("/repos/{owner}/{repository}/pulls")
    suspend fun pullRequestList(
        @Path("owner") owner: String,
        @Path("repository") repository: String
    ): List<PullRequest>

}