package pl.damianhoppe.spring.requestidgenerator.core.idprovider

import jakarta.servlet.http.HttpServletRequest

/**
 * Provider for request id based on request
 */
@FunctionalInterface
fun interface RequestIdProvider {

    /**
     * Gets request id for request
     *
     * @param request The request for which the id will be returned
     * @return Request id or null
     */
    fun getRequestId(request: HttpServletRequest): String?
}