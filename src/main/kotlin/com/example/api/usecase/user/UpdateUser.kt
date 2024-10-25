package com.example.api.usecase.user

import com.example.api.domain.UserStatus
import com.example.api.iservice.repo.IUserRepo
import com.example.api.iservice.repo.RepoUserUpdate
import com.example.api.iusecase.user.IUpdateUser
import com.example.api.iusecase.user.UpdateUserInput
import com.example.api.usecase.TokenAuthenticator
import org.springframework.stereotype.Service

@Service
class UpdateUser(
	private val authenticator: TokenAuthenticator,
	private val userRepo: IUserRepo,
) : IUpdateUser {
	override fun run(input: UpdateUserInput) {
		// Find user
		val user = authenticator.authenticate(input.token)
		if (user == null) {
			throw IllegalArgumentException("User not found")
		}
		if (user.status != UserStatus.ACTIVE) {
			throw IllegalArgumentException("User is not active")
		}

		// Update user
		val updateData =
			RepoUserUpdate(
				name = input.data.name,
				email = user.email,
				status = user.status,
				passwordHash = user.passwordHash,
			)
		userRepo.update(user.id, updateData)
	}
}
