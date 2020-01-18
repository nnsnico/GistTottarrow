package io.nns.tottarrow.infrastracture.api

import io.nns.tottarrow.domain.response.Gist
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GistApi {
    @GET("/users/{user}/gists")
    suspend fun getUserGist(@Path("user") user: String): Response<List<Gist>>
}
