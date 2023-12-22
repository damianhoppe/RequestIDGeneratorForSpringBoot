package pl.damianhoppe.spring.requestidgenerator.interfaces

/**
 * Builder interface for building object instance based on input data
 *
 * @param I The type of input data
 * @param T The type of object to build
 */
@FunctionalInterface
fun interface ObjectBuilder<I, T> {

    /**
     * Creates an instance of an object based on input data
     *
     * @param input Input data to build object
     * @return Built object instance
     */
    fun build(input: I): T
}