package org.example

import org.apache.log4j.PatternLayout
import org.apache.log4j.spi.LoggingEvent

/**
 * This class masks unwanted Log entries,
 * and delegates tasks to Pattern layout
 */
class MaskingLayout : PatternLayout() {
    private val redactedParams = listOf(
        "password",
        "confirmPassword",
        "firstName",
        "lastName",
        "email",
        "with bind values"
    )
    override fun format(loggingEvent: LoggingEvent?): String {
        loggingEvent ?: return ""

        val patternResult = super.format(loggingEvent)

        if (redactedParams.any { patternResult.contains(it, ignoreCase = true) }) {
           return "[REDACTED]"
        }

        return patternResult
    }
}