package com.example.api.usecase

import com.example.api.iservice.repo.IPostRepo
import com.example.api.iusecase.post.GetPostsInput
import com.example.api.iusecase.post.GetPostsOutput
import com.example.api.iusecase.post.IGetPosts
import org.springframework.stereotype.Service

@Service
class GetPosts(
	private val postRepo: IPostRepo,
) : IGetPosts {
	override fun run(input: GetPostsInput): GetPostsOutput {
		val posts = postRepo.findMany(userId = input.userId)
		return GetPostsOutput(posts = posts.map { it.toDomain() })
	}
}
