package io.nns.tottarrow.usecase

import arrow.core.Either
import arrow.core.NonEmptyList
import io.nns.tottarrow.domain.response.Gist
import io.nns.tottarrow.domain.vo.GistError

interface GithubCreateUseCase {
    suspend fun createGist(input: String): Either<GistError, NonEmptyList<Gist>>
}