package io.nns.tottarrow.di

import io.nns.tottarrow.domain.application.GithubCreateInteractor
import io.nns.tottarrow.domain.github.GithubRepository
import io.nns.tottarrow.infrastracture.api.GistApi
import io.nns.tottarrow.infrastracture.github.GithubRepositoryImpl
import io.nns.tottarrow.usecase.GithubCreateUseCase
import org.koin.dsl.module

val appModule = module {
    single { provideGithubRepository(get()) }
    single { provideGistCreateInteractor(get()) }
}

private fun provideGithubRepository(api: GistApi): GithubRepository =
    GithubRepositoryImpl(api)

private fun provideGistCreateInteractor(repository: GithubRepository): GithubCreateUseCase =
    GithubCreateInteractor(repository)
