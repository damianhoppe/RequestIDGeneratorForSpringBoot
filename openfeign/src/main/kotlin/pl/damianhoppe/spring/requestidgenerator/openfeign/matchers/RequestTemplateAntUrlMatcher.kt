package pl.damianhoppe.spring.requestidgenerator.openfeign.matchers

import feign.RequestTemplate
import pl.damianhoppe.spring.requestidgenerator.matchers.AntUrlMatcher

fun RequestTemplate.mapToUrl(): String = this.feignTarget()?.url() + this.url()

/**
 * Implementation of [AntUrlMatcher] for [RequestTemplate]
 */
class RequestTemplateAntUrlMatcher(pattern: String) : AntUrlMatcher<RequestTemplate>(pattern) {

    override fun mapToUrl(obj: RequestTemplate): String {
        return obj.mapToUrl()
    }
}