package com.example.api.service

import com.example.api.iservice.IHasher
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class BcryptHasher : IHasher {
	val bcrypt = BCryptPasswordEncoder()

	override fun hash(data: String): String = bcrypt.encode(data)

	override fun compare(
		hash: String,
		data: String,
	): Boolean = bcrypt.matches(data, hash)
}
