package com.example.api.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.Exception

@RestControllerAdvice
class ExceptionHandler {
	@ExceptionHandler
	fun catchAll(err: Exception) {
		println(err.stackTrace)
		ResponseEntity.internalServerError().body("Internal server error")
	}
}
