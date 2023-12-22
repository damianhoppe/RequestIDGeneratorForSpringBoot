package pl.damianhoppe.reqidgen.sample.mainserver

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.damianhoppe.spring.requestidgenerator.EnableRequestIdGenerator
import pl.damianhoppe.spring.requestidgenerator.subconfig.CoreConfig
import pl.damianhoppe.spring.requestidgenerator.config.RIdGConfig
import pl.damianhoppe.spring.requestidgenerator.config.RIdGConfigurer
import pl.damianhoppe.spring.requestidgenerator.enum.RequestIdGeneratingMode
import pl.damianhoppe.spring.requestidgenerator.subconfig.OpenFeignConfig
import pl.damianhoppe.spring.requestidgenerator.subconfig.MainConfig
import pl.damianhoppe.spring.requestidgenerator.openfeign.matchers.RequestTemplateAntUrlMatcher

@Configuration
@EnableRequestIdGenerator
class RequestIdGeneratorConfiguration {

    @Bean
    fun requestIdGeneratorConfig(configurer: RIdGConfigurer): RIdGConfig {
        return configurer
            .customize<MainConfig> {it
                .requestIdHeaderName("X-Request-Id")
            }
            .customize<CoreConfig> { it
                .mode(RequestIdGeneratingMode.SERVLET_REQUEST_LISTENER)
            }
            .customize<OpenFeignConfig> {it
                .requestMatcher(RequestTemplateAntUrlMatcher("http://second-server/**"))
            }
            .build()
    }
}