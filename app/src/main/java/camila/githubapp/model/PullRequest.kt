package camila.githubapp.model

import java.io.Serializable

data class PullRequest(
    var id: Int,
    var body: String,
    var title: String,
    var full_name: String,
    var user: Owner,
    var html_url: String
): Serializable
