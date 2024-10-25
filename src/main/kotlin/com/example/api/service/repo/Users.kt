package com.example.api.service.repo

import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object Users : Table<Nothing>("users") {
	val id = varchar("id").primaryKey()
	val name = varchar("name")
	val email = varchar("email")
	val status = varchar("status")
	val passwordHash = varchar("password_hash")
}
