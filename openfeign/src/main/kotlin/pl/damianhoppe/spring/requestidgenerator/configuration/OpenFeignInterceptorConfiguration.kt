package pl.damianhoppe.spring.requestidgenerator.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import pl.damianhoppe.spring.requestidgenerator.RIdGConfigHolder
import pl.damianhoppe.spring.requestidgenerator.openfeign.interceptors.RequestIdForOpenFeignInterceptor
import pl.damianhoppe.spring.requestidgenerator.subconfig.OpenFeignConfig
import pl.damianhoppe.spring.requestidgenerator.subconfig.MainConfig

/**
 * Spring configuration providing a RequestInterceptor[RequestIdForOpenFeignInterceptor] which
 * injects request id to outgoing requests
 */
@Configuration
class OpenFeignInterceptorConfiguration {

    @Bean
    @Scope("prototype")
    fun openFeignInterceptor(config: RIdGConfigHolder): RequestIdForOpenFeignInterceptor {
        val openFeignConfig = config.get().getConfig<OpenFeignConfig>()
        val mainConfig = config.get().getConfig<MainConfig>()

        return RequestIdForOpenFeignInterceptor(
            requestMatcher = openFeignConfig.requestMatcher,
            requestIdHeaderName = mainConfig.requestIdHeaderName,
            disabled = openFeignConfig.disabled,
        )
    }
}