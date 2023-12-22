package pl.damianhoppe.spring.requestidgenerator.interceptors

import jakarta.servlet.http.HttpServletRequest

/**
 * Abstract class for request id generation interceptor
 * It allows you to obtain the generated request id
 * and to detect when the generator has finished handling this request
 *
 * The timing of event method calls depends on the implementation of the request id generator
 */
abstract class RequestIdGenerationInterceptor {

    /**
     * Called after assigning an id for the request
     */
    open fun requestIdAssigned(requestId: String, request: HttpServletRequest) {}

    /**
     * Called after the generator finishes processing the request
     */
    open fun requestFinished(requestID: String, request: HttpServletRequest) {}
}