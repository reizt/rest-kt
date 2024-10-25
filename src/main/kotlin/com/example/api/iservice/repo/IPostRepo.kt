package com.example.api.iservice.repo

import com.example.api.domain.Post

data class RepoPost(
	val id: String,
	val userId: String,
	val title: String,
	val content: String,
) {
	fun toDomain(): Post =
		Post(
			id = id,
			userId = userId,
			title = title,
			content = content,
		)
}

data class RepoPostUpdate(
	val userId: String,
	val title: String,
	val content: String,
)

interface IPostRepo {
	fun findMany(userId: String? = null): List<RepoPost>

	fun findById(id: String): RepoPost?

	fun create(data: RepoPost): RepoPost

	fun update(
		id: String,
		data: RepoPostUpdate,
	): Unit

	fun delete(id: String): Unit
}
