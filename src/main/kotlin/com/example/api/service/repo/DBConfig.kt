package com.example.api.service.repo

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class DBConfig(
	@Value("\${db.url}")
	val url: String = "url",
	@Value("\${db.user}")
	val user: String = "user",
	@Value("\${db.password}")
	val password: String = "password",
)
