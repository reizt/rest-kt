package com.example.api.service

import com.example.api.iservice.ISigner
import org.springframework.stereotype.Component

@Component
class JWTSigner : ISigner {
	override fun sign(payload: String, expiresAt: Long): String {
		return payload
	}

	override fun verify(signature: String): String {
		return signature
	}
}
