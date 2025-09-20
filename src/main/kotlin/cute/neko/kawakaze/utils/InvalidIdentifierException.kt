package cute.neko.kawakaze.utils

import org.apache.commons.lang3.StringEscapeUtils

class InvalidIdentifierException : RuntimeException {
    constructor(
        message: String
    ) : super(StringEscapeUtils.escapeJava(message))

    constructor(
        message: String,
        throwable: Throwable
    ) : super(StringEscapeUtils.escapeJava(message), throwable)
}
