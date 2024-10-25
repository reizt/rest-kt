package com.example.api.service.repo

import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object Codes : Table<Nothing>("codes") {
	val id = varchar("id").primaryKey()
	val email = varchar("email")
	val valueHash = varchar("valueHash")
}
