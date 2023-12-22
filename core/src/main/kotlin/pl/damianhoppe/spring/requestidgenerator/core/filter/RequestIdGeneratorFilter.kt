package pl.damianhoppe.spring.requestidgenerator.core.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.apache.tomcat.util.ExceptionUtils
import org.springframework.web.filter.OncePerRequestFilter
import pl.damianhoppe.spring.requestidgenerator.RequestContext
import pl.damianhoppe.spring.requestidgenerator.interceptors.RequestIdGenerationInterceptor

/**
 * Abstract class of filter which is responsible for handle every request and generate ids for them
 */
abstract class RequestIdGeneratorFilter: OncePerRequestFilter() {

    var interceptors: MutableCollection<RequestIdGenerationInterceptor> = mutableListOf()
    protected var noRequestIdPlaceholder: String = ""
    protected val log: Log = LogFactory.getLog(this::class.java)

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        if(doesTheRequestMatch(request)) {
            val requestId: String = getRequestIdFor(request) ?: noRequestIdPlaceholder
            assignRequestId(requestId, request)
            log.debug("Processing request: ${request.requestURI}")
            try {
                filterChain.doFilter(request, response)
            }catch (e: ServletException) {
                error(response, e.cause ?: e)
            }catch (e: Exception) {
                error(response, ExceptionUtils.unwrapInvocationTargetException(e))
            }finally {
                requestProcessingFinished(request, response)
            }
            return
        }
        log.debug("The request was not handled by the request id generator")
        filterChain.doFilter(request, response)
    }

    private fun error(response: HttpServletResponse, e: Throwable) {
        log.error("Failed to complete request: ${e.javaClass.canonicalName ?: e.javaClass.name}: ${e.message}", e)
        response.sendError(500)
    }

    /**
     * Returns request id for incoming request
     *
     * @param request Request for which id should be provided
     * @return request id or null
     */
    protected abstract fun getRequestIdFor(request: HttpServletRequest): String?

    private fun assignRequestId(requestId: String, request: HttpServletRequest) {
        RequestContext.setRequestId(requestId)
        interceptors.forEach {
            try {
                it.requestIdAssigned(requestId, request)
            }catch (e: Exception) {
                log.error(e.message, e)
            }
        }
        log.debug("Assigned requestId: $requestId for request: ${request.requestURI}")
        afterAssigningRequestId(requestId, request)
    }

    /**
     * Method called after assigning request id for incoming request and called before all interceptors are called.
     *
     * @param requestId Assigned request id
     * @param request Incoming request for which requestId is assigned
     */
    protected open fun afterAssigningRequestId(requestId: String, request: HttpServletRequest) { }

    private fun requestProcessingFinished(request: HttpServletRequest, response: HttpServletResponse) {
        val requestId = RequestContext.requestId ?: ""
        interceptors.forEach {
            try {
                it.requestFinished(requestId, request)
            }catch (e: Exception) {
                log.error(e.message, e)
            }
        }
        try {
            afterRequestProcessingFinished(request, response)
        }finally {
            RequestContext.clear()
        }
    }

    /**
     * Method called after request processing finished, also called after all interceptors are called.
     *
     * @param request Request finished
     * @param response Outgoing response
     */
    protected open fun afterRequestProcessingFinished(request: HttpServletRequest, response: HttpServletResponse) { }

    /**
     * Checks whether the request matches to be handled by the generator
     *
     * @param request Incoming request for which requestId is assigned
     * @return true if request matches, false if not
     */
    protected abstract fun doesTheRequestMatch(request: HttpServletRequest): Boolean
}