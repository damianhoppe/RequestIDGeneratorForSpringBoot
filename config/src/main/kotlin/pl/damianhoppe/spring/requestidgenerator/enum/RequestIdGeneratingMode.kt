package pl.damianhoppe.spring.requestidgenerator.enum

/**
 * Request id generating mode.
 *
 * Available modes:
 * [SERVLET_REQUEST_LISTENER], [FILTER]
 */
enum class RequestIdGeneratingMode {
    /**
     * The request id generator within the ServletRequestListener event listener.
     * Handles the request before it is handled by the servlet or filter
     * and ends after the request exits the last servlet or the last filter in the chain.
     */
    SERVLET_REQUEST_LISTENER,

    /**
     * The request id generator in the filter.
     */
    FILTER
}