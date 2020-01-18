package io.nns.tottarrow.domain.application

import arrow.core.Either
import arrow.core.NonEmptyList
import arrow.core.extensions.either.applicativeError.applicativeError
import arrow.core.fix
import arrow.core.flatMap
import arrow.integrations.retrofit.adapter.unwrapBody
import io.nns.tottarrow.domain.github.GithubRepository
import io.nns.tottarrow.domain.response.Gist
import io.nns.tottarrow.domain.vo.GistError
import io.nns.tottarrow.usecase.GithubCreateUseCase

class GithubCreateInteractor(private val repository: GithubRepository) : GithubCreateUseCase {
    override suspend fun createGist(input: String): Either<GistError, List<Gist>> =
        repository.get(input)
            .unsafeRunSync()
            .unwrapBody(Either.applicativeError())
            .fix()
            .mapLeft { GistError.UserNotFound }
            .flatMap { resp ->
                NonEmptyList
                    .fromList(resp)
                    .toEither { GistError.ResultNotFound }
                    .map { it.toList() }
            }
}
