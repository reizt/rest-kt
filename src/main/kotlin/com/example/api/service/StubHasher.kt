package com.example.api.service

import com.example.api.iservice.IHasher
import org.springframework.stereotype.Component

@Component
class StubHasher : IHasher {
	override fun hash(data: String): String = data

	override fun compare(
		hash: String,
		data: String,
	): Boolean = hash == data
}
