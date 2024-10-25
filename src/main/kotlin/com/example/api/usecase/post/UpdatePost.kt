package com.example.api.usecase

import com.example.api.domain.UserStatus
import com.example.api.iservice.repo.IPostRepo
import com.example.api.iservice.repo.RepoPostUpdate
import com.example.api.iusecase.post.IUpdatePost
import com.example.api.iusecase.post.UpdatePostInput
import org.springframework.stereotype.Service

@Service
class UpdatePost(
	private val authenticator: TokenAuthenticator,
	private val postRepo: IPostRepo,
) : IUpdatePost {
	override fun run(input: UpdatePostInput) {
		// Authenticate token
		val user = authenticator.authenticate(input.token)
		if (user == null) {
			throw IllegalArgumentException("Token is invalid")
		}
		if (user.status != UserStatus.ACTIVE) {
			throw IllegalArgumentException("User is not active")
		}

		// Find post
		val post = postRepo.findById(input.postId)
		if (post == null) {
			throw IllegalArgumentException("Post not found")
		}

		// Update post
		val updateData =
			RepoPostUpdate(
				userId = user.id,
				title = input.data.title,
				content = input.data.content,
			)
		postRepo.update(post.id, updateData)
	}
}
