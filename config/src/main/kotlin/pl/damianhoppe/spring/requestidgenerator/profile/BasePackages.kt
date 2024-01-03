package pl.damianhoppe.spring.requestidgenerator.profile

import org.springframework.boot.autoconfigure.AutoConfigurationPackages
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ComponentScan

/**
 * Returns a collection of base packages that may contain component classes to be scanned.
 * Consists of:
 * - autoconfiguration packages for ApplicationContext.autowireCapableBeanFactory
 * - base package of class annotated with
 * - base packages  of class specified in ComponentScan annotations in all beans loaded in ApplicationContext
 */
fun findBasePackages(app: ApplicationContext): Collection<String> {
    val basePackages = mutableSetOf<String>()
    try {
        basePackages.addAll(AutoConfigurationPackages.get(app.autowireCapableBeanFactory))
    }catch (_: IllegalStateException) { }
    app.getBeanNamesForAnnotation(SpringBootApplication::class.java).forEach { beanName ->
        app.getType(beanName)?.packageName?.let { basePackages.add(it) }
        app.findAllAnnotationsOnBean(beanName, ComponentScan::class.java, false).forEach { componentScan ->
            basePackages.addAll(componentScan.basePackages)
            basePackages.addAll(componentScan.basePackageClasses.map { it.java.packageName })
        }
    }
    app.getBeanNamesForAnnotation(ComponentScan::class.java).forEach { beanName ->
        app.findAnnotationOnBean(beanName, ComponentScan::class.java)?.let { componentScan ->
            basePackages.addAll(componentScan.basePackages)
            basePackages.addAll(componentScan.basePackageClasses.map { it.java.packageName })
        }
    }
    return basePackages
}