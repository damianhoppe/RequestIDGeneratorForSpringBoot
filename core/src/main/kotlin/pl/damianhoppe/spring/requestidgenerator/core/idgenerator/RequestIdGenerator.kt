package pl.damianhoppe.spring.requestidgenerator.core.idgenerator

/**
 * Request id generator
 */
@FunctionalInterface
fun interface RequestIdGenerator {

    /**
     * Generate new request id
     *
     * @return new request id
     */
    fun generateNewRequestId(): String
}