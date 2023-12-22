package pl.damianhoppe.spring.requestidgenerator.openfeign.interceptors

import feign.RequestInterceptor
import feign.RequestTemplate
import org.apache.commons.logging.LogFactory
import pl.damianhoppe.spring.requestidgenerator.RequestContext
import pl.damianhoppe.spring.requestidgenerator.interfaces.Matcher
import pl.damianhoppe.spring.requestidgenerator.matchers.AlwaysMatches
import pl.damianhoppe.spring.requestidgenerator.openfeign.matchers.mapToUrl

/**
 * Default request interceptor for openFeign requests for which request ids can be injected
 */
open class RequestIdForOpenFeignInterceptor(
    val requestIdHeaderName: String,
    val requestMatcher: Matcher<RequestTemplate> = AlwaysMatches(),
    val disabled: Boolean = false,
) : RequestInterceptor {

    private val log = LogFactory.getLog(this::class.java)

    init {
        if(disabled)
            log.info("Request id injection in openFeign request is disabled")
    }

    override fun apply(template: RequestTemplate) {
        if(disabled)
            return
        if(!requestMatcher.matches(template)) {
            log.debug("Url not matches: ${template.mapToUrl()}")
            return
        }else {
            log.debug("Url matches: ${template.mapToUrl()}")
        }
        val requestId = RequestContext.requestId
        if(requestId != null)
            template.header(requestIdHeaderName, requestId)
    }
}