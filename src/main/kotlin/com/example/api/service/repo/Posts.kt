package com.example.api.service.repo

import org.ktorm.schema.Table
import org.ktorm.schema.varchar

object Posts : Table<Nothing>("posts") {
	val id = varchar("id").primaryKey()
	val userId = varchar("user_id")
	val title = varchar("title")
	val content = varchar("content")
}
