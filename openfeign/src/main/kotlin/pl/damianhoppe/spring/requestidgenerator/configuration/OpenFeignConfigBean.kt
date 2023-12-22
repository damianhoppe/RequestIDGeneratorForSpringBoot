package pl.damianhoppe.spring.requestidgenerator.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import pl.damianhoppe.spring.requestidgenerator.RIdGProperties
import pl.damianhoppe.spring.requestidgenerator.config.RIdGConfig
import pl.damianhoppe.spring.requestidgenerator.subconfig.OpenFeignConfig

/**
 * Spring configuration for [OpenFeignConfig] bean
 */
@Configuration
class OpenFeignConfigBean {

    /**
     * Provide a prototype bean of [OpenFeignConfig]
     * to enable it to be loaded into the final configuration[RIdGConfig]
     */
    @Bean
    @Scope("prototype")
    fun openFeignConfig(properties: RIdGProperties) = OpenFeignConfig(properties)
}