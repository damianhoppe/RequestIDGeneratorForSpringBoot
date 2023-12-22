package pl.damianhoppe.spring.requestidgenerator.profile

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.beans.BeansException

class ConfigurationProfileUtils

private val log = LogFactory.getLog(ConfigurationProfileUtils::class.java)

/**
 * Creates an instance of the class using ApplicationContext.autowireCapableBeanFactory
 *
 * @throws BeansException on failed creating instance
 * @return Object instance
 */
fun createInstance(clazz: Class<*>, app: ApplicationContext): Any {
    return app.autowireCapableBeanFactory.autowire(clazz, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false)
}

/**
 * Creates an object based on the definition contained in the object instance.
 * Definition should be an argument-free method annotated with [Provider] and should return an instance
 * of the object being searched for.
 *
 * @param objectInstance An instance of an object that can contain a provider
 * @param objectClass Class of object to create from provider
 * @return Instance of object or null if no provider is found
 */
fun getObjectFrom(objectInstance: Any, objectClass: Class<*>): Any? {
    val method = objectInstance.javaClass.methods.find {
        if(!it.isAnnotationPresent(Provider::class.java))
            return@find false
        if(!it.returnType.isAssignableFrom(objectClass)) {
            return@find false
        }
        if(it.parameterCount == 0) {
            return@find true
        }else {
            log.warn("Found bean provider for ${objectClass.simpleName} but skipped, because has arguments: ${it.parameterCount}. Arguments injection is not supported.")
            return@find false
        }
    } ?: return null
    return method.invoke(objectInstance)
}

/**
 * Similar to [getObjectFrom] but cast bean to specific class
 *
 * @param objectInstance An instance of an object that can contain a provider
 * @param objectClass Class of object to create from provider
 * @return Instance of bean or null if no definition is found
 * @see getObjectFrom
 */
inline fun <reified C> getObjectOfTypeFrom(objectInstance: Any, objectClass: Class<C>): C? {
    val b = getObjectFrom(objectInstance, objectClass) ?: return null
    return b as C
}