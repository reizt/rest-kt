package com.example.api.iusecase.post

data class UpdatePostInputData(
	val title: String,
	val content: String,
)

data class UpdatePostInput(
	val token: String,
	val postId: String,
	val data: UpdatePostInputData,
)

interface IUpdatePost {
	fun run(input: UpdatePostInput): Unit
}
