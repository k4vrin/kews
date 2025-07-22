package dev.kavrin.kews.core.network

enum class StatusCode(val code: Int, val description: String) {
    Continue(100, "Continue"),
    SwitchingProtocols(101, "Switching Protocols"),
    OK(200, "OK"),
    Created(201, "Created"),
    Accepted(202, "Accepted"),
    NoContent(204, "No Content"),
    MovedPermanently(301, "Moved Permanently"),
    Found(302, "Found"),
    SeeOther(303, "See Other"),
    NotModified(304, "Not Modified"),
    BadRequest(400, "Bad Request"),
    Unauthorized(401, "Unauthorized"),
    Forbidden(403, "Forbidden"),
    NotFound(404, "Not Found"),
    MethodNotAllowed(405, "Method Not Allowed"),
    RequestTimeout(408, "Request Timeout"),
    Conflict(409, "Conflict"),
    Gone(410, "Gone"),
    LengthRequired(411, "Length Required"),
    PreconditionFailed(412, "Precondition Failed"),
    PayloadTooLarge(413, "Payload Too Large"),
    UnsupportedMediaType(415, "Unsupported Media Type"),
    InternalServerError(500, "Internal Server Error"),
    NotImplemented(501, "Not Implemented"),
    BadGateway(502, "Bad Gateway"),
    ServiceUnavailable(503, "Service Unavailable"),
    GatewayTimeout(504, "Gateway Timeout"),
    HTTPVersionNotSupported(505, "HTTP Version Not Supported"),
    Unknown(-1, "Unknown");

    companion object {
        private val codeMap: Map<Int, StatusCode> = entries.associateBy { it.code }
        fun from(code: Int): StatusCode = codeMap[code] ?: Unknown
    }
}

// Extension properties for common status code checks.
val StatusCode.isSuccessful: Boolean
    get() = code in 200..299

val StatusCode.isClientError: Boolean
    get() = code in 400..499

val StatusCode.isServerError: Boolean
    get() = code in 500..599