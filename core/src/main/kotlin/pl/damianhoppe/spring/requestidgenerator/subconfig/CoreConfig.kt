package pl.damianhoppe.spring.requestidgenerator.subconfig

import jakarta.servlet.http.HttpServletRequest
import pl.damianhoppe.spring.requestidgenerator.interfaces.ObjectBuilder
import pl.damianhoppe.spring.requestidgenerator.RIdGProperties
import pl.damianhoppe.spring.requestidgenerator.config.RIdGConfig
import pl.damianhoppe.spring.requestidgenerator.enum.RequestIdGeneratingMode
import pl.damianhoppe.spring.requestidgenerator.config.RIdGSubConfig
import pl.damianhoppe.spring.requestidgenerator.core.idgenerator.RequestIdGenerator
import pl.damianhoppe.spring.requestidgenerator.enum.RequestIdProvidingStrategy
import pl.damianhoppe.spring.requestidgenerator.core.filter.RequestIdGeneratorFilter
import pl.damianhoppe.spring.requestidgenerator.interceptors.RequestIdGenerationInterceptor
import pl.damianhoppe.spring.requestidgenerator.interfaces.Matcher
import pl.damianhoppe.spring.requestidgenerator.matchers.AlwaysMatches
import pl.damianhoppe.spring.requestidgenerator.core.servletrequestlistener.ReqIdGenServletRequestListener

/**
 * Stores config for core module
 */
class CoreConfig(
    properties: RIdGProperties,
    defaultRequestIdGenerator: RequestIdGenerator,
    defaultFilterBuilder: ObjectBuilder<RIdGConfig, RequestIdGeneratorFilter>,
    defaultServletRequestListenerBuilder: ObjectBuilder<RIdGConfig, ReqIdGenServletRequestListener>,
): RIdGSubConfig {

    /**
     * Request id generator.
     */
    var requestIdGenerator: RequestIdGenerator = defaultRequestIdGenerator
        private set
    /**
     * Changes request id generator.
     * @return [CoreConfig] for further customizations
     */
    fun requestIdGenerator(requestIdGenerator: RequestIdGenerator) = apply { this.requestIdGenerator = requestIdGenerator }

    /**
     * A placeholder for the request id if it cannot be obtained (it was not read
     * from the request or generated, depends on [RequestIdProvidingStrategy])
     */
    var placeholder: String = properties.requestId.placeholder
        private set
    /**
     * Changes placeholder for no request id
     * @return [CoreConfig] for further customizations
     */
    fun placeholder(placeHolder: String) = apply { this.placeholder = placeHolder }

    /**
     * Builder for [RequestIdGeneratorFilter]
     */
    var filterBuilder: ObjectBuilder<RIdGConfig, RequestIdGeneratorFilter> = defaultFilterBuilder
        private set
    /**
     * Changes builder for [RequestIdGeneratorFilter].
     *
     * Important:
     * The filter builder is responsible for the filter configuration,
     * so they can pass configuration parameters in a different way than the default builder,
     * they can edit them or set their own.
     * In this case, some defined parameters may not affect the behavior of custom filter.
     *
     * @return [CoreConfig] for further customizations
     */
    fun filterBuilder(filterBuilder: ObjectBuilder<RIdGConfig, RequestIdGeneratorFilter>) = apply { this.filterBuilder = filterBuilder }

    /**
     * Builder for [ReqIdGenServletRequestListener]
     */
    var servletRequestListenerBuilder: ObjectBuilder<RIdGConfig, ReqIdGenServletRequestListener> = defaultServletRequestListenerBuilder
        private set
    /**
     * Changes builder for [ReqIdGenServletRequestListener]
     *
     * Important:
     * The servlet request listener builder is responsible for the listener configuration,
     * so they can pass configuration parameters in a different way than the default builder,
     * they can edit them or set their own.
     * In this case, some defined parameters may not affect the behavior of custom servlet request listener.
     * @return [CoreConfig] for further customizations
     */
    fun servletRequestListenerBuilder(servletRequestListenerBuilder: ObjectBuilder<RIdGConfig, ReqIdGenServletRequestListener>) = apply { this.servletRequestListenerBuilder = servletRequestListenerBuilder }

    /**
     * Order of filter[RequestIdGeneratorFilter] built by [filterBuilder]
     */
    var filterOrder: Int = properties.filter.order
        private set
    /**
     * Changes order of filter[RequestIdGeneratorFilter] built by [filterBuilder]
     * @return [CoreConfig] for further customizations
     */
    fun filterOrder(order: Int) = apply { this.filterOrder = order }

    /**
     * Request id providing strategy
     * @see RequestIdProvidingStrategy
     */
    var requestIdProvidingStrategy: RequestIdProvidingStrategy = properties.requestId.providingStrategy
        private set

    /**
     * Changes request id providing strategy
     * @see RequestIdProvidingStrategy
     * @return [CoreConfig] for further customizations
     */
    fun requestIdProvidingStrategy(strategy: RequestIdProvidingStrategy) = apply { this.requestIdProvidingStrategy = strategy }


    /**
     * Collection of request id generator interceptors
     * @see RequestIdGenerationInterceptor
     */
    val interceptors: MutableCollection<RequestIdGenerationInterceptor> = mutableListOf()

    /**
     * Adds request id generator interceptor
     * @see RequestIdGenerationInterceptor
     * @return [CoreConfig] for further customizations
     */
    fun addInterceptor(interceptor: RequestIdGenerationInterceptor) = apply { this.interceptors.add(interceptor) }


    /**
     * UrlMatcher checking incoming requests.
     * Only for matching requests, ids are generated, interceptors are called, etc
     */
    var requestMatcher: Matcher<HttpServletRequest> = AlwaysMatches()
        private set

    /**
     * Changing the url matcher for incoming requests for which ids are generated
     * @return [CoreConfig] for further customizations
     */
    fun requestMatcher(requestMatcher: Matcher<HttpServletRequest>) = apply { this.requestMatcher = requestMatcher }

    /**
     * Sets the default url matcher which allow all requests for incoming requests for which ids are generated
     */
    fun defaultUrlMatcher() = apply { this.requestMatcher = AlwaysMatches() }

    /**
     * Request id generating mode
     * @see RequestIdGeneratingMode
     */
    var mode: RequestIdGeneratingMode = properties.mode
        private set

    /**
     * Changes request id generating mode
     * @see RequestIdGeneratingMode
     * @return [CoreConfig] for further customizations
     */
    fun mode(mode: RequestIdGeneratingMode) = apply { this.mode = mode }

    /**
     * Disables request id generation
     */
    var disabled: Boolean = properties.requestId.generationDisabled
        private set
    /**
     * Disables request id generation
     * @return [CoreConfig] for further customizations
     */
    fun disable() = apply { this.disabled = true }
}