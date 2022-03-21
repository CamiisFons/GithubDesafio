package camila.githubapp.model

import java.io.Serializable

data class Repository(
    var id: Int,
    var name: String,
    var full_name: String,
    var description: String,
    var owner: Owner,
    var stargazers_count: Int,
    var forks: Int
) : Serializable
