package pl.damianhoppe.spring.requestidgenerator.core.servletrequestlistener

import jakarta.servlet.http.HttpServletRequest
import pl.damianhoppe.spring.requestidgenerator.interfaces.ObjectBuilder
import pl.damianhoppe.spring.requestidgenerator.config.RIdGConfig
import pl.damianhoppe.spring.requestidgenerator.subconfig.CoreConfig
import pl.damianhoppe.spring.requestidgenerator.core.idprovider.RequestIdProvider
import pl.damianhoppe.spring.requestidgenerator.core.idprovider.getRequestIdProvider
import pl.damianhoppe.spring.requestidgenerator.interfaces.Matcher

/**
 * Default request id generator as ServletRequestListener which generates request ids based on configuration[RIdGConfig]
 */
class DefaultReqIdGenServletRequestListener(
    private val requestIdProvider: RequestIdProvider,
    private val requestMatcher: Matcher<HttpServletRequest>,
    noRequestIdPlaceholder: String = "",
) : ReqIdGenServletRequestListener() {

    init {
        this.noRequestIdPlaceholder = noRequestIdPlaceholder
    }

    override fun getRequestIdFor(request: HttpServletRequest): String? {
        return requestIdProvider.getRequestId(request)
    }

    override fun doestRequestMatch(request: HttpServletRequest): Boolean {
        return requestMatcher.matches(request)
    }

    class Builder: ObjectBuilder<RIdGConfig, ReqIdGenServletRequestListener> {

        override fun build(input: RIdGConfig): DefaultReqIdGenServletRequestListener {
            val coreConfig = input.getConfig<CoreConfig>()

            return DefaultReqIdGenServletRequestListener(
                requestIdProvider = getRequestIdProvider(input),
                requestMatcher = coreConfig.requestMatcher,
                noRequestIdPlaceholder = coreConfig.placeholder
            )
        }
    }
}