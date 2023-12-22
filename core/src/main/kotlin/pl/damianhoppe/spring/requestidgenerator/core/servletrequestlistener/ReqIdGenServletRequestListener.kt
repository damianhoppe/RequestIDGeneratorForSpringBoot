package pl.damianhoppe.spring.requestidgenerator.core.servletrequestlistener

import jakarta.servlet.ServletRequestEvent
import jakarta.servlet.ServletRequestListener
import jakarta.servlet.http.HttpServletRequest
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import pl.damianhoppe.spring.requestidgenerator.RequestContext
import pl.damianhoppe.spring.requestidgenerator.interceptors.RequestIdGenerationInterceptor

/**
 * Abstract class of RequestServletListener which is responsible for handle every request and generate ids for them
 */
abstract class ReqIdGenServletRequestListener : ServletRequestListener {

    var interceptors: MutableCollection<RequestIdGenerationInterceptor> = mutableListOf()
    protected var noRequestIdPlaceholder: String = ""
    protected val log: Log = LogFactory.getLog(this::class.java)

    override fun requestInitialized(sre: ServletRequestEvent?) {
        val request = sre?.servletRequest as? HttpServletRequest
        if(request != null) {
            if(doestRequestMatch(request)) {
                val requestId: String = getRequestIdFor(request) ?: noRequestIdPlaceholder
                assignRequestId(requestId, request)
            }
        }
        super.requestInitialized(sre)
    }

    override fun requestDestroyed(sre: ServletRequestEvent?) {
        try {
            val request = sre?.servletRequest as? HttpServletRequest
            if(request != null) {
                val requestId = RequestContext.requestId ?: ""
                interceptors.forEach {
                    try {
                        it.requestFinished(requestId, request)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                afterRequestProcessingFinished(request)
            }
        }finally {
            RequestContext.clear()
        }
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
                e.printStackTrace()
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

    /**
     * Method called after request processing finished, also called after all interceptors are called.
     *
     * @param request Request finished
     */
    protected open fun afterRequestProcessingFinished(request: HttpServletRequest) { }

    /**
     * Checks whether the request matches to be handled by the generator
     *
     * @param request Incoming request for which requestId is assigned
     * @return true if request matches, false if not
     */
    protected abstract fun doestRequestMatch(request: HttpServletRequest): Boolean
}