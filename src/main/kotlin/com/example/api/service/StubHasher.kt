package com.example.api.service

import com.example.api.iservice.IHasher
import org.springframework.stereotype.Component

@Component
class Argon2Hasher : IHasher {
	override fun hash(data: String): String {
		return data
	}

	override fun compare(hash: String, data: String): Boolean {
		return hash == data
	}
}
