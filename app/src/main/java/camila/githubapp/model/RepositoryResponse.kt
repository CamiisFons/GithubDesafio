package camila.githubapp.model

import java.io.Serializable

data class RepositoryResponse(
    val items: List<Repository>
) : Serializable
