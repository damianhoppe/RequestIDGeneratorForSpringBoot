package pl.damianhoppe.spring.requestidgenerator.logback.interceptors

import jakarta.servlet.http.HttpServletRequest
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.slf4j.MDC
import pl.damianhoppe.spring.requestidgenerator.interceptors.RequestIdGenerationInterceptor


/**
 * Request id generation interceptor for MDC.
 * Puts request id into MDC under specific key and removes it after the request is processed.
 *
 * @param mdcKey The name of the key associated with the request id in MDC
 */
class MDCRequestIdInterceptor(
    private val mdcKey: String,
): RequestIdGenerationInterceptor() {

    private val log: Log = LogFactory.getLog(this::class.java)

    override fun requestIdAssigned(requestId: String, request: HttpServletRequest) {
        MDC.put(mdcKey, requestId)
        log.debug("Assigned requestId in MDC[$mdcKey]:\"$requestId\" for request: ${request.requestURI}")
    }

    override fun requestFinished(requestID: String, request: HttpServletRequest) {
        MDC.remove(mdcKey)
        log.debug("Removed MDC[$mdcKey] for request: ${request.requestURI}")
    }
}