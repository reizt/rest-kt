package com.example.api.service.repo

import com.example.api.domain.UserStatus
import com.example.api.iservice.repo.IUserRepo
import com.example.api.iservice.repo.RepoUser
import com.example.api.iservice.repo.RepoUserDelete
import com.example.api.iservice.repo.RepoUserUpdate
import org.ktorm.database.Database
import org.ktorm.dsl.QueryRowSet
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.select
import org.ktorm.dsl.update
import org.ktorm.dsl.where
import org.springframework.stereotype.Repository

@Repository
class UserRepo(
	val config: DBConfig,
) : IUserRepo {
	private val db =
		Database.connect(
			url = config.url,
			user = config.user,
			password = config.password,
		)

	private fun toInterfaceUser(row: QueryRowSet): RepoUser =
		RepoUser(
			id = row[Users.id]!!,
			name = row[Users.name]!!,
			email = row[Users.email]!!,
			status = UserStatus.fromString(row[Users.status]!!),
			passwordHash = row[Users.passwordHash]!!,
		)

	override fun findById(id: String): RepoUser? {
		val query = db.from(Users).select().where { Users.id eq id }
		return query.rowSet.let { toInterfaceUser(it) }
	}

	override fun findByEmail(email: String): RepoUser? {
		val query = db.from(Users).select().where { Users.email eq email }
		return query.rowSet.let { toInterfaceUser(it) }
	}

	override fun create(data: RepoUser): RepoUser {
		db.insert(Users) {
			set(it.id, data.id)
			set(it.name, data.name)
			set(it.email, data.email)
			set(it.status, data.status.value)
			set(it.passwordHash, data.passwordHash)
		}
		return data
	}

	override fun update(
		id: String,
		data: RepoUserUpdate,
	) {
		db.update(Users) {
			set(it.name, data.name)
			set(it.email, data.email)
			set(it.status, data.status.value)
			set(it.passwordHash, data.passwordHash)
			where {
				it.id eq id
			}
		}
	}

	override fun delete(where: RepoUserDelete) {
		db.delete(Users) {
			Users.id eq where.id
		}
	}
}
