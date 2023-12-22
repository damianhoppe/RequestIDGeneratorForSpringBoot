package pl.damianhoppe.spring.requestidgenerator.core.error

import jakarta.servlet.http.HttpServletRequest
import org.apache.commons.logging.LogFactory
import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.ErrorAttributes
import pl.damianhoppe.spring.requestidgenerator.RIdGConfigHolder
import pl.damianhoppe.spring.requestidgenerator.subconfig.MainConfig

/**
 * ErrorController which adds attribute with request id to error response.
 *
 * @param requestAttributeNameOverride If not null, override default requestAttributeName
 * from MainConfig, which specifies the name of the request attribute in which the request id is saved
 * @param responseAttributeName The name of the attribute in the error response that request id is associated, if any
 *
 * Default: "errorId"
 */
class WithIdErrorController(
    protected val errorAttributes: ErrorAttributes,
    serverProperties: ServerProperties,
    config: RIdGConfigHolder,
    requestAttributeNameOverride: String? = null,
    val responseAttributeName: String = "errorId"
    ) : BasicErrorController(errorAttributes, serverProperties.error) {

    private final val requestAttributeName: String = requestAttributeNameOverride ?: config.get().getConfig<MainConfig>().requestIdAttributeName
    private final val log = LogFactory.getLog(javaClass)

    override fun getErrorAttributes(
        request: HttpServletRequest?,
        options: ErrorAttributeOptions?
    ): MutableMap<String, Any> {
        val attributes = super.getErrorAttributes(request, options)
        if (request != null) {
            tryToAssignRequestIdToAttributes(request, attributes)
        }
        return attributes
    }

    fun tryToAssignRequestIdToAttributes(request: HttpServletRequest, attributes: MutableMap<String, Any>) {
        val requestId = request.getAttribute(requestAttributeName)
        if(requestId == null) {
            log.debug("Request id not found in request attributes under name $requestAttributeName")
            return
        }
        if(requestId !is String) {
            log.debug("Request attribute[$requestAttributeName] has incorrect type: ${requestId::class.java.simpleName}, expected: ${String::class.java.simpleName}")
            return
        }
        attributes[responseAttributeName] = requestId
    }
}