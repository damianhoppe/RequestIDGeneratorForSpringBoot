package pl.damianhoppe.spring.requestidgenerator.openfeign.matchers

import feign.RequestTemplate
import feign.mock.MockTarget
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import pl.damianhoppe.spring.requestidgenerator.openfeign.matchers.RequestTemplateAntUrlMatcher
import pl.damianhoppe.spring.requestidgenerator.openfeign.matchers.mapToUrl

class RequestTemplateAntUrlMatcherTest {

    @Test
    fun mapToUrl() {
        val target = object: MockTarget<Any>(Any::class.java) {
            override fun name(): String = "server"
            override fun url(): String = "http://server"
        }
        val request = RequestTemplate()
        request.uri("/get")
        request.feignTarget(target)

        val matcher = RequestTemplateAntUrlMatcher("http://server/get")
        assertEquals(request.mapToUrl(), matcher.mapToUrl(request))
    }
}