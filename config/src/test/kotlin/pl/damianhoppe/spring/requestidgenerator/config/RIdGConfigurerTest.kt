package pl.damianhoppe.spring.requestidgenerator.config

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class RIdGConfigurerTest {

    @Test
    fun customize_ShouldUpdateConfig() {
        val newName = "123"
        val configurer = RIdGConfigurer(
            mapOf(TestConfig::class.java to TestConfig(""))
        )
        configurer.customize<TestConfig> {
            it.name = newName
        }
        assertEquals(newName, (configurer.configs[TestConfig::class.java] as TestConfig).name)
    }

    @Test
    fun customize_ShouldThrowIllegalStateException_WhenConfigToCustomizeNotFound() {
        val newName = "123"
        val configurer = RIdGConfigurer()
        assertThrows(IllegalArgumentException::class.java) {
            configurer.customize<TestConfig> {
                it.name = newName
            }
        }
    }

    @Test
    fun tryCustomize_ShouldUpdateConfig() {
        val newName = "123"
        val configurer = RIdGConfigurer(
            mapOf(TestConfig::class.java to TestConfig(""))
        )
        configurer.tryCustomize<TestConfig> {
            it.name = newName
        }
        assertEquals(newName, (configurer.configs[TestConfig::class.java] as TestConfig).name)
    }

    @Test
    fun tryCustomize_ShouldThrowIllegalStateException_WhenConfigToCustomizeNotFound() {
        val configurer = RIdGConfigurer()
        assertDoesNotThrow {
            configurer.tryCustomize<TestConfig> {
                it.name = "123"
            }
        }
    }

    @Test
    fun build_shouldBuildValidObjectContainingAllConfigurations() {
        val newName = "123"
        val configurer = RIdGConfigurer(
            mapOf(TestConfig::class.java to TestConfig(""))
        )
        configurer.customize<TestConfig> {
            it.name = newName
        }
        val config = configurer.build()
        assertEquals(configurer.configs.size, config.configs.size)
        assertEquals(newName, (config.configs[TestConfig::class.java] as TestConfig).name)
    }

    @Test
    fun build_configsCorrectnessVerification_ShouldThrowException_WhenConfigIsIncorrect() {
        val configurer = RIdGConfigurer(
            mapOf(TestConfig::class.java to object: TestConfig("") {
                override fun verifyCorrectness() {
                    assert(name.isNotBlank())
                }
            })
        )
        assertThrows(AssertionError::class.java) {
            configurer.build()
        }
    }
}

open class TestConfig(var name: String) : RIdGSubConfig