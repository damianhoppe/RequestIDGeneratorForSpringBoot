package pl.damianhoppe.spring.requestidgenerator

import pl.damianhoppe.spring.requestidgenerator.config.RIdGConfig

/**
 * Stores config of request id generator and all its submodules.
 */
class RIdGConfigHolder(
    val config: RIdGConfig
) {

    /**
     * Returns holding [RIdGConfig]
     */
    fun get() = this.config
}