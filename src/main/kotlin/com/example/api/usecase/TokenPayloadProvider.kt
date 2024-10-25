package com.example.api.usecase

import com.google.gson.Gson
import org.springframework.stereotype.Component

data class TokenPayload(
	val userId: String,
)

@Component
class TokenPayloadProvider {
	private val gson = Gson()

	fun serialize(payload: TokenPayload): String = gson.toJson(payload)

	fun deserialize(token: String): TokenPayload = gson.fromJson(token, TokenPayload::class.java)
}
