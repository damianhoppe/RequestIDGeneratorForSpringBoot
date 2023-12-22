package pl.damianhoppe.spring.requestidgenerator.profile

import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AnnotationTypeFilter

/**
 * Scans basePackages and return found classes annotated with [ConfigurationProfile]
 *
 * @see ConfigurationProfile
 */
fun findConfigurationProfileComponents(basePackages: Collection<String>): Map<ConfigurationProfile, Class<*>> {
    val map = mutableMapOf<ConfigurationProfile, Class<*>>()
    val scanner = ClassPathScanningCandidateComponentProvider(true)
    scanner.addIncludeFilter(AnnotationTypeFilter(ConfigurationProfile::class.java))
    for(basePackage in basePackages)
        for(component in scanner.findCandidateComponents(basePackage)) {
            val profileClass: Class<*>
            try {
                profileClass = Class.forName(component.beanClassName)
            }catch (_: Exception) {
                continue
            }
            if(!profileClass.isAnnotationPresent(ConfigurationProfile::class.java))
                continue
            val configurationProfile = profileClass.getAnnotation(ConfigurationProfile::class.java)
            map[configurationProfile] = profileClass
        }
    return map
}