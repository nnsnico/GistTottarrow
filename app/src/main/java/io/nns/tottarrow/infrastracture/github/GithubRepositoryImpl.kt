package io.nns.tottarrow.infrastracture.github

import arrow.fx.IO
import arrow.fx.fix
import io.nns.tottarrow.domain.github.GithubRepository
import io.nns.tottarrow.domain.response.Gist
import io.nns.tottarrow.infrastracture.api.GistApi
import retrofit2.Response

class GithubRepositoryImpl(private val api: GistApi) : GithubRepository {

    override suspend fun get(userName: String): IO<Response<List<Gist>>> =
        IO.just(api.getUserGist(userName)).fix()
}