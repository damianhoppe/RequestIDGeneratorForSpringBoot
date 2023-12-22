package pl.damianhoppe.spring.requestidgenerator.configuration.profiles

import pl.damianhoppe.spring.requestidgenerator.RIdGProperties
import pl.damianhoppe.spring.requestidgenerator.profile.ConfigurationProfile
import pl.damianhoppe.spring.requestidgenerator.enum.RequestIdProvidingStrategy
import pl.damianhoppe.spring.requestidgenerator.profile.Provider

/**
 * Configuration profile for servers that receive only trusted requests, internal services with which only other
 * services communicate - identifiers are read from the request if they are included in it, otherwise they are generated
 */
@ConfigurationProfile("internal")
class InternalConfigurationProfile {

    @Provider
    private fun internalDefaultProperties(): RIdGProperties {
        return RIdGProperties().apply {
            this.requestId.providingStrategy = RequestIdProvidingStrategy.AUTO
        }
    }
}