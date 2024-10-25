package com.example.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class RestApi

fun main(args: Array<String>) {
	runApplication<RestApi>(*args)
}
