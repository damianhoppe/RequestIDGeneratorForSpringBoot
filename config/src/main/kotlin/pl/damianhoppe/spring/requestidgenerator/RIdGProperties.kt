package pl.damianhoppe.spring.requestidgenerator

import org.springframework.boot.context.properties.ConfigurationProperties
import pl.damianhoppe.spring.requestidgenerator.enum.RequestIdGeneratingMode
import pl.damianhoppe.spring.requestidgenerator.enum.RequestIdProvidingStrategy

/**
 * A property class for storing the properties loaded from the configuration file.
 * If properties are not specified in the configuration file, they have default values.
 */
@ConfigurationProperties(prefix = "ridg")
class RIdGProperties {
    companion object {
        const val DEFAULT_REQUEST_ID_HEADER = "X-Request-Id"
        const val DEFAULT_REQUEST_ID_ATTRIBUTE = "X-Request-Id"
        const val DEFAULT_MDC_REQUEST_ID = "RequestId"
    }

    var mode: RequestIdGeneratingMode = RequestIdGeneratingMode.SERVLET_REQUEST_LISTENER
    val requestId = RequestIdProperties()
    val filter = FilterProperties()
    val openFeign = OpenFeignProperties()
    val logs = LogsProperties()

    class RequestIdProperties {
        var headerName: String = DEFAULT_REQUEST_ID_HEADER
        var attributeName: String = DEFAULT_REQUEST_ID_ATTRIBUTE
        var providingStrategy: RequestIdProvidingStrategy = RequestIdProvidingStrategy.AUTO
        var placeholder: String = "--"
        var generationDisabled: Boolean = false
    }

    class FilterProperties {
        var order: Int = Integer.MIN_VALUE + 100
    }

    class OpenFeignProperties {
        var disableInterceptor: Boolean = false
    }

    class LogsProperties {
        var mdcKey: String = DEFAULT_MDC_REQUEST_ID
    }
}