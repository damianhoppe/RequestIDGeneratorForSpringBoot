package pl.damianhoppe.spring.requestidgenerator.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.damianhoppe.spring.requestidgenerator.RIdGConfigHolder
import pl.damianhoppe.spring.requestidgenerator.config.RIdGConfig
import pl.damianhoppe.spring.requestidgenerator.core.interceptors.AddRequestAttributeInterceptor
import pl.damianhoppe.spring.requestidgenerator.subconfig.MainConfig

/**
 * Spring configuration providing default request id generator interceptors:
 * [AddRequestAttributeInterceptor]
 */
@Configuration
class DefaultInterceptorsConfiguration {

    lateinit var config: RIdGConfig

    @Autowired
    fun loadConfig(configHolder: RIdGConfigHolder) {
        this.config = configHolder.get()
    }

    @Bean
    fun addRequestAttributeInterceptor(): AddRequestAttributeInterceptor {
        val attributeName: String = config.getConfig<MainConfig>().requestIdAttributeName
        return AddRequestAttributeInterceptor(attributeName)
    }
}