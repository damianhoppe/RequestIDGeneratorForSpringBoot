package pl.damianhoppe.spring.requestidgenerator.core.filter

import jakarta.servlet.http.HttpServletRequest
import pl.damianhoppe.spring.requestidgenerator.interfaces.ObjectBuilder
import pl.damianhoppe.spring.requestidgenerator.config.RIdGConfig
import pl.damianhoppe.spring.requestidgenerator.subconfig.CoreConfig
import pl.damianhoppe.spring.requestidgenerator.core.idprovider.RequestIdProvider
import pl.damianhoppe.spring.requestidgenerator.core.idprovider.getRequestIdProvider
import pl.damianhoppe.spring.requestidgenerator.interfaces.Matcher
import pl.damianhoppe.spring.requestidgenerator.matchers.AlwaysMatches
import pl.damianhoppe.spring.requestidgenerator.matchers.UrlMatcher

/**
 * Default request id generator as Filter which generates request ids based on configuration[RIdGConfig]
 */
open class DefaultRequestIdGeneratorFilter(
    private val requestIdProvider: RequestIdProvider,
    private val requestMatcher: Matcher<HttpServletRequest>,
    noRequestIdPlaceholder: String = "",
) : RequestIdGeneratorFilter() {

    init {
        this.noRequestIdPlaceholder = noRequestIdPlaceholder
    }

    override fun getRequestIdFor(request: HttpServletRequest): String? {
        return requestIdProvider.getRequestId(request)
    }

    override fun doesTheRequestMatch(request: HttpServletRequest): Boolean {
        return requestMatcher.matches(request)
    }

    class Builder : ObjectBuilder<RIdGConfig, RequestIdGeneratorFilter> {

        override fun build(input: RIdGConfig): RequestIdGeneratorFilter {
            val coreConfig = input.getConfig<CoreConfig>()

            return DefaultRequestIdGeneratorFilter(
                requestIdProvider = getRequestIdProvider(input),
                requestMatcher = coreConfig.requestMatcher,
                noRequestIdPlaceholder = coreConfig.placeholder
            )
        }
    }
}