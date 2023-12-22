package pl.damianhoppe.spring.requestidgenerator.core.idprovider

import pl.damianhoppe.spring.requestidgenerator.enum.RequestIdProvidingStrategy
import pl.damianhoppe.spring.requestidgenerator.config.RIdGConfig
import pl.damianhoppe.spring.requestidgenerator.subconfig.MainConfig
import pl.damianhoppe.spring.requestidgenerator.subconfig.CoreConfig

fun getRequestIdProvider(config: RIdGConfig): RequestIdProvider {
    val mainConfig = config.getConfig<MainConfig>()
    val coreConfig = config.getConfig<CoreConfig>()
    return when(coreConfig.requestIdProvidingStrategy) {
        RequestIdProvidingStrategy.READ -> {
            RequestIdProvider {
                it.getHeader(mainConfig.requestIdHeaderName)
            }
        }
        RequestIdProvidingStrategy.GENERATE -> {
            RequestIdProvider {
                coreConfig.requestIdGenerator.generateNewRequestId()
            }
        }
        RequestIdProvidingStrategy.AUTO -> {
            RequestIdProvider {
                it.getHeader(mainConfig.requestIdHeaderName) ?: coreConfig.requestIdGenerator.generateNewRequestId()
            }
        }
    }
}