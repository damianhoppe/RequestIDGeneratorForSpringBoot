package pl.damianhoppe.spring.requestidgenerator.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.damianhoppe.spring.requestidgenerator.RIdGProperties
import pl.damianhoppe.spring.requestidgenerator.interceptors.RequestIdGenerationInterceptor
import pl.damianhoppe.spring.requestidgenerator.logback.interceptors.MDCRequestIdInterceptor

/**
 * Spring configuration providing [RequestIdGenerationInterceptor] which puts request id to MDC
 * and remove it from MDC.
 */
@Configuration
class MdcConfiguration {

    @Bean
    fun requestIdGenInterceptorForLogback(properties: RIdGProperties): RequestIdGenerationInterceptor {
        return MDCRequestIdInterceptor(properties.logs.mdcKey)
    }
}