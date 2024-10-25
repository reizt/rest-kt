package com.example.api.iservice.repo

data class RepoCode(
	val id: String,
	val email: String,
	val valueHash: String,
)

data class RepoCodeWhere(
	val id: String? = null,
	val email: String? = null,
)

data class RepoCodeDelete(
	val id: String,
)

interface ICodeRepo {
	fun findById(id: String): RepoCode?

	fun create(data: RepoCode): RepoCode

	fun delete(where: RepoCodeDelete): Unit
}
