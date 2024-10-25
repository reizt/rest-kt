package com.example.api.iservice

interface ISigner {
	fun sign(
		payload: String,
		expiresAt: Long,
	): String

	fun verify(signature: String): String
}
