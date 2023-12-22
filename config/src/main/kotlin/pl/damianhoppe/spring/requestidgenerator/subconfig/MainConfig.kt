package pl.damianhoppe.spring.requestidgenerator.subconfig

import org.springframework.context.ApplicationContext
import pl.damianhoppe.spring.requestidgenerator.RIdGProperties
import pl.damianhoppe.spring.requestidgenerator.config.RIdGSubConfig

/**
 * Stores main config for all modules of request id generator
 */
class MainConfig(
    app: ApplicationContext,
    properties: RIdGProperties,
) : RIdGSubConfig {

    /**
     * Name of header with request id.
     * Uses to read request id from incoming http request.
     */
    var requestIdHeaderName: String = properties.requestId.headerName
        private set

    /**
     * Renames the header with request id from which it is read.
     *
     * @return [MainConfig] for further customizations
     */
    fun requestIdHeaderName(requestIdHeaderName: String) = apply { this.requestIdHeaderName = requestIdHeaderName }

    /**
     * Name of attribute with request id.
     * Uses to save request id in attributes of incoming http request.
     */
    var requestIdAttributeName: String = properties.requestId.attributeName
        private set

    /**
     * Changes name of request attribute name in which the request id is saved.
     *
     * @return [MainConfig] for further customizations
     */
    fun requestIdAttributeName(requestIdAttributeName: String) = apply { this.requestIdAttributeName = requestIdAttributeName }

    override fun verifyCorrectness() {
        assert(requestIdHeaderName.isNotBlank()) { "requestIdHeaderName cannot be blank" }
        assert(requestIdAttributeName.isNotBlank()) { "requestIdAttributeName cannot be blank" }
    }
}