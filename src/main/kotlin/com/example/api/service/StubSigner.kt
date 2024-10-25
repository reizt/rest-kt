package com.example.api.service

import com.example.api.iservice.ISigner
import org.springframework.stereotype.Component

@Component
class StubSigner : ISigner {
	override fun sign(
		payload: String,
		expiresAt: Long,
	): String = payload

	override fun verify(signature: String): String = signature
}
