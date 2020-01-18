package io.nns.tottarrow.domain.response

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class Gist(
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    val owner: Owner,
    val files: Map<String, File>,
    val description: String?
)

@JsonSerializable
data class Owner(
    val login: String,
    @Json(name = "avatar_url")
    val avatarUrl: String
)

@JsonSerializable
data class File(
    val filename: String?,
    val language: String?
)

fun Map<String, File>.toFileList() : List<File> = this.values.toList()
