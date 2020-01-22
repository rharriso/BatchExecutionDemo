package org.example

import org.apache.log4j.Layout
import org.apache.log4j.PatternLayout
import org.apache.log4j.spi.LoggingEvent

/**
 * This class masks unwanted Log entries,
 * and delegates tasks to Pattern layout
 */
class MaskingLayout : Layout() {
    internal val redactedParams  = listOf(
        "password",
        "confirmPassword",
        "firstName",
        "lastName",
        "email"
    ).joinToString("|")

    private val jsonKeyValueRegex = """"?($redactedParams)"?\s*:(([^"][^\s,}]+)|("[^"]+"))""".toRegex(RegexOption.IGNORE_CASE)
    private val jsonReplacePattern = """"$1": [REDACTED]"""

    private val graphqlKeyValueRegex = """"?($redactedParams)"?\s*=\s*(([^"][^\s,}]+)|("[^"]+"))""".toRegex(RegexOption.IGNORE_CASE)
    private val graphqlReplacePattern = """$1=[REDACTED]"""

    // protobuf messages
    private val httpOutgoingRegex = """http-outgoing.*($redactedParams).*""".toRegex(RegexOption.IGNORE_CASE)
    private val httpReplacePattern = "[REDACTED]"

    // query bind values out
    private val jooqRegex = """.*with bind values.*""".toRegex(RegexOption.IGNORE_CASE)
    private val jooqReplace = "[REDACTED]"

    var conversionPattern: String
        get() = patternLayout.conversionPattern
        set(value) {
            patternLayout.conversionPattern = value
        }

    private val patternLayout = PatternLayout()

    override fun format(loggingEvent: LoggingEvent?): String {
        loggingEvent ?: return ""

        val patternResult = patternLayout.format(loggingEvent)
        return patternResult.replace(jsonKeyValueRegex, jsonReplacePattern)
            .replace(graphqlKeyValueRegex, graphqlReplacePattern)
            .replace(httpOutgoingRegex, httpReplacePattern)
            .replace(jooqRegex, jooqReplace)
    }

    override fun ignoresThrowable(): Boolean {
        return patternLayout.ignoresThrowable()
    }

    override fun activateOptions() {
        return patternLayout.activateOptions()
    }
}