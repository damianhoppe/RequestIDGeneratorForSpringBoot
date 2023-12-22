package pl.damianhoppe.spring.requestidgenerator.enum

/**
 * Defines how to get the request id in the request id generator
 *
 * Available providing strategies:
 * [AUTO], [READ], [GENERATE]
 */
enum class RequestIdProvidingStrategy {
    /**
     * Automatically - tries to read the id from the request,
     * if it does not exist, it generates a new id
     */
    AUTO,

    /**
     * Read the id from request, if it does not exist, return null
     */
    READ,

    /**
     * Always generate new request id for incoming request
     */
    GENERATE,
}