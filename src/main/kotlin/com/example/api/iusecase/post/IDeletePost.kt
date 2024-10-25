package com.example.api.iusecase.post

data class DeletePostInput(
	val token: String,
	val postId: String,
)

interface IDeletePost {
	fun run(input: DeletePostInput): Unit
}
