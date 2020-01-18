package io.nns.tottarrow.domain.github

import arrow.fx.IO
import io.nns.tottarrow.domain.response.Gist
import retrofit2.Response

interface GithubRepository {
    suspend fun get(userName: String): IO<Response<List<Gist>>>
}