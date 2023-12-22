package pl.damianhoppe.reqidgen.sample.mainserver

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient("second-server")
interface SecondServerApi {

    @GetMapping("/hello")
    fun hello(): String?
}