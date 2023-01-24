package net.red.green.gitsearch.user.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserAccount(
    @Json(name = "login") val login: String?,
    @Json(name = "avatar_url") val avatar_url: String?,
    @Json(name = "url") val url: String?
)

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "items") val items: List<UserAccount>
)
