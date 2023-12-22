package pl.damianhoppe.spring.requestidgenerator.profile

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import pl.damianhoppe.spring.requestidgenerator.RIdGProperties
import pl.damianhoppe.spring.requestidgenerator.config.RIdGConfig

class ConfigurationProfileUtilsKtTest {

    val noArgsBeanProvider = NoArgsBeanProvider()
    val withArgsBeanProvider = WithArgsBeanProvider()
    val noPropertiesProvider = NoPropertiesProvider()
    val withoutProviderAnnotation = WithoutProviderAnnotation()

    @Test
    fun getBean_ShouldReturnObjectInstance_ForAnnotatedNoArgumentsMethod() {
        assertEquals(RIdGProperties::class.java, getObjectFrom(noArgsBeanProvider, RIdGProperties::class.java)?.javaClass)
    }

    @Test
    fun getBean_ShouldReturnNull_WhenAnnotatedMethodWithoutArgumentsNotFound() {
        assertNull(getObjectFrom(withArgsBeanProvider, RIdGProperties::class.java))
    }

    @Test
    fun getBean_ShouldReturnNull_ForAnnotatedNoMethodFoundForTargetClassType() {
        assertNull(getObjectFrom(noPropertiesProvider, RIdGProperties::class.java))
    }

    @Test
    fun getBean_ShouldReturnNull_ForNoAnnotatedMethod() {
        val bean: RIdGProperties? = getObjectOfTypeFrom(withoutProviderAnnotation, RIdGProperties::class.java)
        assertEquals(null, bean)
    }

    @Test
    fun getBeanOfType_ShouldReturnTargetObjectInstance_ForExistingBeanProvider() {
        val bean: RIdGProperties? = getObjectOfTypeFrom(noArgsBeanProvider, RIdGProperties::class.java)
        assertEquals(RIdGProperties::class.java, bean?.javaClass)
    }
}

class NoArgsBeanProvider {
    @Provider
    fun skippedPropertiesProviderBecauseHasArguments(unusedArg: String) = RIdGProperties()
    @Provider
    fun defaultPropertiesProvider() = RIdGProperties()
}

class WithArgsBeanProvider {
    @Provider
    fun skippedPropertiesProviderBecauseHasArguments(unusedArg: String) = RIdGProperties()
}

class NoPropertiesProvider {
    @Provider
    fun configProvider() = RIdGConfig()
}

class WithoutProviderAnnotation {
    fun defaultPropertiesProvider() = RIdGProperties()
}