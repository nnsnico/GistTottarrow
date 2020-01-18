package io.nns.tottarrow.infrastracture

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.nns.tottarrow.di.ApplicationJsonAdapterFactory
import io.nns.tottarrow.domain.response.Gist
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class MockGithubServer {
    private val server: MockWebServer = MockWebServer()
    val responseFixture = GithubResponseFixture
    lateinit var url: String

    fun start() {
        server.start(41414)
        url = "http://${server.hostName}:${server.port}"
    }

    fun enqueueGistResponse() {
        val testAdapter: JsonAdapter<List<Gist>> = Moshi.Builder()
            .add(ApplicationJsonAdapterFactory.INSTANCE)
            .build()
            .adapter(
                Types.newParameterizedType(
                    List::class.java,
                    Gist::class.java
                )
            )

        server.enqueue(
            MockResponse().setBody(
                testAdapter.toJson(responseFixture.successResponseFixture.body())
            )
        )
        server.enqueue(
            MockResponse().setBody(
                testAdapter.toJson(responseFixture.failureResponseFixture.body())
            )
        )
    }

    fun shutdown() {
        server.shutdown()
    }
}