package pl.damianhoppe.spring.requestidgenerator.core.interceptors

import jakarta.servlet.http.HttpServletRequest
import org.apache.commons.logging.LogFactory
import pl.damianhoppe.spring.requestidgenerator.interceptors.RequestIdGenerationInterceptor

/**
 * Request id generation interceptor which adds generated request id to request attributes
 *
 * @param attributeName Name of attribute with which request id is associated
 */
class AddRequestAttributeInterceptor(private val attributeName: String): RequestIdGenerationInterceptor() {

    private val log = LogFactory.getLog(this::class.java)

    override fun requestIdAssigned(requestId: String, request: HttpServletRequest) {
        log.debug("Assigned requestId to attributes: [$attributeName]:\"$requestId\"")
        request.setAttribute(attributeName, requestId)
        request
    }
}