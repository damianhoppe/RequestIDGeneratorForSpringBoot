package pl.damianhoppe.spring.requestidgenerator.configuration

import jakarta.servlet.Filter
import jakarta.servlet.ServletRequestListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.damianhoppe.spring.requestidgenerator.RIdGConfigHolder
import pl.damianhoppe.spring.requestidgenerator.RIdGProperties
import pl.damianhoppe.spring.requestidgenerator.subconfig.CoreConfig
import pl.damianhoppe.spring.requestidgenerator.enum.RequestIdGeneratingMode
import pl.damianhoppe.spring.requestidgenerator.interceptors.RequestIdGenerationInterceptor

/**
 * Spring configuration providing Filter or RequestServletListener which generates requests ids
 */
@Configuration("core.requestIdGeneratorConfiguration")
class RequestIdGeneratorConfiguration {

    @Autowired
    lateinit var app: ApplicationContext

    @Autowired
    lateinit var properties: RIdGProperties

    @Autowired(required = false)
    val requestIdGenerationInterceptors: List<RequestIdGenerationInterceptor> = emptyList()

    @Bean
    fun requestIdGeneratorFilter(config: RIdGConfigHolder): FilterRegistrationBean<Filter> {
        val coreConfig = config.get().getConfig<CoreConfig>()
        val filter = (coreConfig.filterBuilder).build(config.get())
        filter.interceptors.addAll(coreConfig.interceptors)
        filter.interceptors.addAll(requestIdGenerationInterceptors)

        val registrationBean = FilterRegistrationBean<Filter>()
        registrationBean.filter = filter
        registrationBean.order = coreConfig.filterOrder
        registrationBean.isEnabled = coreConfig.mode == RequestIdGeneratingMode.FILTER && !coreConfig.disabled
        return registrationBean
    }

    @Bean
    fun eventListener(config: RIdGConfigHolder): ServletRequestListener {
        val coreConfig = config.get().getConfig<CoreConfig>()

        if(coreConfig.mode != RequestIdGeneratingMode.SERVLET_REQUEST_LISTENER || coreConfig.disabled)
            return object: ServletRequestListener { }
        val requestServletListener = (coreConfig.servletRequestListenerBuilder).build(config.get())
        requestServletListener.interceptors.addAll(coreConfig.interceptors)
        requestServletListener.interceptors.addAll(requestIdGenerationInterceptors)

        return requestServletListener
    }
}