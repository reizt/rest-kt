package com.example.api.service.repo

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class DBConfig(
	@Value("\${spring.datasource.url}")
	val url: String = "url",
	@Value("\${spring.datasource.user}")
	val user: String = "user",
	@Value("\${spring.datasource.password}")
	val password: String = "password",
)
