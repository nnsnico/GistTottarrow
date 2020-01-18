package io.nns.tottarrow.infrastracture

import io.nns.tottarrow.domain.response.File
import io.nns.tottarrow.domain.response.Gist
import io.nns.tottarrow.domain.response.Owner
import okhttp3.internal.EMPTY_RESPONSE
import retrofit2.Response

object GithubResponseFixture {

    val successResponseFixture: Response<List<Gist>> =
        Response.success(
            listOf(
                Gist(
                    createdAt = "2019-12-31T02:15:15+09:00",
                    updatedAt = "2020-01-02T11:34:15+09:00",
                    owner = Owner(
                        login = "test",
                        avatarUrl = "http://test.jp/test.png"
                    ),
                    files = mapOf(
                        "sample.txt" to File("sample.txt", null)
                    ),
                    description = "this is test"
                )
            )
        )


    val failureResponseFixture: Response<List<Gist>> =
        Response.error(400, EMPTY_RESPONSE)
}
