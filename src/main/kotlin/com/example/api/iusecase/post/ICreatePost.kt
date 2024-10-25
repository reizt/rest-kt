package com.example.api.iusecase.post

import com.example.api.domain.Post

data class CreatePostInputData(
	val title: String,
	val content: String,
)

data class CreatePostInput(
	val token: String,
	val data: CreatePostInputData,
)

data class CreatePostOutput(
	val post: Post,
)

interface ICreatePost {
	fun run(input: CreatePostInput): CreatePostOutput
}
