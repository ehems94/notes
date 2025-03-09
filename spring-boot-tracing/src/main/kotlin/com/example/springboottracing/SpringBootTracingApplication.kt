package com.example.springboottracing

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBootTracingApplication

fun main(args: Array<String>) {
    runApplication<SpringBootTracingApplication>(*args)
}
