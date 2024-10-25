package com.example.api.iservice

interface IHasher {
	fun hash(data: String): String

	fun compare(
		hash: String,
		data: String,
	): Boolean
}
