package com.example.api.service.repo

import com.example.api.iservice.repo.ICodeRepo
import com.example.api.iservice.repo.RepoCode
import com.example.api.iservice.repo.RepoCodeDelete
import org.ktorm.database.Database
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.select
import org.ktorm.dsl.where
import org.springframework.stereotype.Repository

@Repository
class CodeRepo(
	val config: DBConfig,
) : ICodeRepo {
	private val db =
		Database.connect(
			url = config.url,
			user = config.user,
			password = config.password,
		)

	private fun toInterfaceCode(row: QueryRowSet): RepoCode =
		RepoCode(
			id = row[Codes.id]!!,
			email = row[Codes.email]!!,
			valueHash = row[Codes.valueHash]!!,
		)

	override fun findById(id: String): RepoCode? {
		val query = db.from(Codes).select().where { Codes.id eq id }
		return query.rowSet.let { toInterfaceCode(it) }
	}

	override fun create(data: RepoCode): RepoCode {
		db.insert(Codes) {
			set(it.id, data.id)
			set(it.email, data.email)
			set(it.valueHash, data.valueHash)
		}
		return data
	}

	override fun delete(where: RepoCodeDelete) {
		db.delete(Codes) {
			Codes.id eq where.id
		}
	}
}
