package com.example.api.iusecase.post

import com.example.api.domain.Post

data class GetPostsInput(
	val userId: String? = null,
)

data class GetPostsOutput(
	val posts: List<Post>,
)

interface IGetPosts {
	fun run(input: GetPostsInput): GetPostsOutput
}
