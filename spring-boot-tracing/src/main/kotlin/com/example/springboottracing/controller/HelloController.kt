package com.example.springboottracing.controller

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    private val logger = LoggerFactory.getLogger(HelloController::class.java)

    @GetMapping("/hello/{name}")
    fun sayHello(@PathVariable name: String): HelloResponse {
        logger.info("Received request for name: $name")
        return HelloResponse("Hello, $name!")
    }
}

data class HelloResponse(
    val message: String
)
