package com.example.api.usecase.post

import com.example.api.domain.UserStatus
import com.example.api.iservice.repo.IPostRepo
import com.example.api.iservice.repo.RepoPost
import com.example.api.iusecase.post.CreatePostInput
import com.example.api.iusecase.post.CreatePostOutput
import com.example.api.iusecase.post.ICreatePost
import com.example.api.usecase.TokenAuthenticator
import com.example.api.usecase.generateId
import org.springframework.stereotype.Service

@Service
class CreatePost(
	private val authenticator: TokenAuthenticator,
	private val postRepo: IPostRepo,
) : ICreatePost {
	override fun run(input: CreatePostInput): CreatePostOutput {
		// Authenticate token
		val user = authenticator.authenticate(input.token)
		if (user == null) {
			throw IllegalArgumentException("Token is invalid")
		}
		if (user.status != UserStatus.ACTIVE) {
			throw IllegalArgumentException("User is not active")
		}

		// Create post
		val post =
			RepoPost(
				id = generateId(),
				userId = user.id,
				title = input.data.title,
				content = input.data.content,
			)
		postRepo.create(post)

		return CreatePostOutput(post = post.toDomain())
	}
}
