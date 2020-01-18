package io.nns.tottarrow.domain.vo


enum class GistError(val code: Int, val message: String) {
    ResultNotFound(404, "HTTP 404 Result Not Found"),
    UserNotFound(404, "HTTP 404 User Not Found")
}