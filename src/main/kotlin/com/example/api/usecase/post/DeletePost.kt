package com.example.api.usecase.post

import com.example.api.domain.UserStatus
import com.example.api.iservice.repo.IPostRepo
import com.example.api.iusecase.post.DeletePostInput
import com.example.api.iusecase.post.IDeletePost
import com.example.api.usecase.TokenAuthenticator
import org.springframework.stereotype.Service

@Service
class DeletePost(
	private val authenticator: TokenAuthenticator,
	private val postRepo: IPostRepo,
) : IDeletePost {
	override fun run(input: DeletePostInput) {
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

		// Delete post
		postRepo.delete(post.id)
	}
}
