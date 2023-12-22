package pl.damianhoppe.spring.requestidgenerator.core.idgenerator

import java.util.*

/**
 * UUID request id generator
 */
class RequestIdGeneratorUUID: RequestIdGenerator {

    /**
     * Generate new request id based on UUID
     *
     * @return new random UUID
     */
    override fun generateNewRequestId(): String = UUID.randomUUID().toString()
}