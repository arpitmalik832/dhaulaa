package com.paytag.web

import java.io.IOException

/**
 * [ApiException]
 */
class ApiException : Exception {
    val kind: Kind?
    val code: Int

    constructor(kind: Kind?, msg: String?) : this(
        kind,
        -1,
        msg
    ) {
    }

    constructor(
        kind: Kind?,
        msg: String?,
        cause: Throwable
    ) : this(kind, -1, msg, cause) {
    }

    constructor(
        kind: Kind?,
        code: Int,
        msg: String?
    ) : super(msg) {
        this.kind = kind
        this.code = code
    }

    constructor(
        kind: Kind?,
        code: Int,
        msg: String?,
        cause: Throwable
    ) : super(msg, cause) {
        this.kind = kind
        this.code = code
    }

    /**
     * Identifies the event kind which triggered a [ApiException].
     */
    enum class Kind {
        /**
         * An [IOException] occurred while communicating to the server.
         */
        NETWORK,
        /**
         * An exception was thrown while (de)serializing a body.
         */
        CONVERSION,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,
        /**
         * call close_white is null
         */
        NULL,
        /**
         *
         */
        SOCKET_TIMEOUT,
        /**
         * Exception kind for invalid request
         */
        INVALID_REQUEST,
        /**
         * Exception kind for invalid response
         */
        INVALID_RESPONSE,
        /**
         * Exception for incompatible data cache version
         */
        CACHE_EXCEPTION,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED
    }
}