package pl.damianhoppe.spring.requestidgenerator.subconfig

import feign.RequestTemplate
import pl.damianhoppe.spring.requestidgenerator.RIdGProperties
import pl.damianhoppe.spring.requestidgenerator.config.RIdGSubConfig
import pl.damianhoppe.spring.requestidgenerator.interfaces.Matcher
import pl.damianhoppe.spring.requestidgenerator.matchers.AlwaysMatches

/**
 * Stores config for openFeign module
 */
class OpenFeignConfig(
    properties: RIdGProperties
) : RIdGSubConfig {

    /**
     * Disables interceptor
     */
    var disabled: Boolean = properties.openFeign.disableInterceptor
        private set
    /**
     * Disables interceptor
     * @return [OpenFeignConfig] for further customizations
     */
    fun disable() = apply { this.disabled = true }

    /**
     * A matcher that checks outgoing requests.
     * Only for matching requests, ids are injected
     */
    var requestMatcher: Matcher<RequestTemplate> = AlwaysMatches()
        private set

    /**
     * Changing the matcher for outgoing requests for which ids are injected
     * @return [OpenFeignConfig] for further customizations
     */
    fun requestMatcher(requestMatcher: Matcher<RequestTemplate>) = apply { this.requestMatcher = requestMatcher }
}