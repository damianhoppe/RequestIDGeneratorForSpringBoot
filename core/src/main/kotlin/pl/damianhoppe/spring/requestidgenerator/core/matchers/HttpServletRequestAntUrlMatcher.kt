package pl.damianhoppe.spring.requestidgenerator.core.matchers

import jakarta.servlet.http.HttpServletRequest
import pl.damianhoppe.spring.requestidgenerator.matchers.AntUrlMatcher

/**
 * Implementation of [AntUrlMatcher] for [HttpServletRequest]
 */
class HttpServletRequestAntUrlMatcher(pattern: String) : AntUrlMatcher<HttpServletRequest>(pattern) {

    override fun mapToUrl(obj: HttpServletRequest): String {
        return obj.requestURI
    }
}