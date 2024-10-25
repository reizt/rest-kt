package com.example.api.iservice.repo

import com.example.api.domain.User
import com.example.api.domain.UserStatus

data class RepoUser(
	val id: String,
	val name: String,
	val email: String,
	val status: UserStatus,
	val passwordHash: String,
) {
	fun toDomain(): User =
		User(
			id = id,
			name = name,
			email = email,
			status = status,
		)
}

data class RepoUserUpdate(
	val name: String,
	val email: String,
	val status: UserStatus,
	val passwordHash: String,
)

data class RepoUserDelete(
	val id: String,
)

interface IUserRepo {
	fun findById(id: String): RepoUser?

	fun findByEmail(email: String): RepoUser?

	fun create(data: RepoUser): RepoUser

	fun update(
		id: String,
		data: RepoUserUpdate,
	): Unit

	fun delete(where: RepoUserDelete): Unit
}
