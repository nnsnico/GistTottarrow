package io.nns.tottarrow.infrastracture.github

import io.kotlintest.specs.BehaviorSpec
import io.nns.tottarrow.di.networkModule
import io.nns.tottarrow.infrastracture.MockGithubServer
import io.nns.tottarrow.infrastracture.api.GistApi
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Retrofit

class GithubRepositoryTest : BehaviorSpec(), KoinTest {
    private val server = MockGithubServer()
    private val retrofit: Retrofit by inject(named("mockClient")) {
        parametersOf(server.url)
    }

    private fun setup() {
        server.enqueueGistResponse()
        server.start()

        startKoin { modules(networkModule) }
    }

    init {
        setup()

        val api = retrofit.create(GistApi::class.java)
        val repository = GithubRepositoryImpl(api)

        val response = runBlocking { repository.get("test") }
        val invalidResponse = runBlocking { repository.get("invalid") }

        given("GithubRepositoryImpl") {
            `when`("called #get valid request") {
                then("should be Right") {
                    println(response)
                    assert(response!!.isNotEmpty())
                }
            }
            `when`("called #get invalid request") {
                then("should be Left") {
                    println(invalidResponse)
                    assert(invalidResponse.isNullOrEmpty())
                }
            }
        }

        finish()
    }

    private fun finish() {
        server.shutdown()
    }
}

