package camila.githubapp.model

import java.io.Serializable

data class Owner(
    var login: String,
    var avatar_url: String?
): Serializable
