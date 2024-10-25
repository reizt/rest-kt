package com.example.api.domain

data class User(
	val id: String,
	val name: String,
	val email: String,
	val status: UserStatus,
)

enum class UserStatus(
	val value: String,
) {
	UNVERIFIED("unverified"),
	ACTIVE("active"),
	INACTIVE("inactive"),
	;

	override fun toString(): String = value

	companion object {
		fun fromString(value: String): UserStatus =
			when (value) {
				"unverified" -> UNVERIFIED
				"active" -> ACTIVE
				"inactive" -> INACTIVE
				else -> throw IllegalArgumentException("Invalid UserStatus value: $value")
			}
	}
}
