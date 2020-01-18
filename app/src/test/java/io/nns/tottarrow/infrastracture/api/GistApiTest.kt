package io.nns.tottarrow.infrastracture.api

import io.kotlintest.specs.BehaviorSpec
import io.nns.tottarrow.di.networkModule
import io.nns.tottarrow.infrastracture.MockGithubServer
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Retrofit

class GistApiTest : BehaviorSpec(), KoinTest {
    private val server: MockGithubServer = MockGithubServer()
    private val retrofit: Retrofit by inject(named("mockClient")) { parametersOf(server.url) }

    private fun setup() {
        server.enqueueGistResponse()
        server.start()

        startKoin { modules(networkModule) }
    }

    init {
        setup()

        val client = retrofit.create(GistApi::class.java)

        val response = runBlocking {
            client.getUserGist("test")
        }

        val invalidResponse = runBlocking {
            client.getUserGist("invalid")
        }

        given("Gist api response") {
            `when`("called #getUserGist valid request") {
                then("should be equals to mock response by valid request") {
                    println(response.body())
                    assert(server.responseFixture.successResponseFixture.body() == response.body())
                }
            }
            `when`("called #getUserGist invalid request") {
                then("should be equals to mock response by invalid request") {
                    println(invalidResponse.code())
                    assert(server.responseFixture.failureResponseFixture.body() == invalidResponse.body())
                }
            }
        }

        finish()
    }

    private fun finish() {
        server.shutdown()
    }
}
