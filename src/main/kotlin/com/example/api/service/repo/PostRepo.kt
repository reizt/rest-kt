package com.example.api.service.repo

import com.example.api.iservice.repo.IPostRepo
import com.example.api.iservice.repo.RepoPost
import com.example.api.iservice.repo.RepoPostUpdate
import org.ktorm.database.Database
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.update
import org.ktorm.dsl.where
import org.springframework.stereotype.Repository

@Repository
class PostRepo(
	val config: DBConfig,
) : IPostRepo {
	private val db =
		Database.connect(
			url = config.url,
			user = config.user,
			password = config.password,
		)

	private fun toInterfacePost(row: QueryRowSet): RepoPost =
		RepoPost(
			id = row[Posts.id]!!,
			userId = row[Posts.userId]!!,
			title = row[Posts.title]!!,
			content = row[Posts.content]!!,
		)

	override fun findMany(userId: String?): List<RepoPost> {
		val query = db.from(Posts).select()
		if (userId != null) {
			query.where { Posts.userId eq userId }
		}
		return query.map { toInterfacePost(it) }
	}

	override fun findById(id: String): RepoPost? {
		val query = db.from(Posts).select().where { Posts.id eq id }
		return query.rowSet.let { toInterfacePost(it) }
	}

	override fun create(data: RepoPost): RepoPost {
		db.insert(Posts) {
			set(it.id, data.id)
			set(it.userId, data.userId)
			set(it.title, data.title)
			set(it.content, data.content)
		}
		return data
	}

	override fun update(
		id: String,
		data: RepoPostUpdate,
	) {
		db.update(Posts) {
			set(it.userId, data.userId)
			set(it.title, data.title)
			set(it.content, data.content)
			where {
				it.id eq id
			}
		}
	}

	override fun delete(id: String) {
		db.delete(Posts) { Posts.id eq id }
	}
}
