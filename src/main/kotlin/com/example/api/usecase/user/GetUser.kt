package com.example.api.usecase.user

import com.example.api.iservice.repo.IUserRepo
import com.example.api.iusecase.user.GetUserInput
import com.example.api.iusecase.user.GetUserOutput
import com.example.api.iusecase.user.IGetUser
import com.example.api.usecase.TokenAuthenticator
import org.springframework.stereotype.Service

@Service
class GetUser(
	private val authenticator: TokenAuthenticator,
	private val userRepo: IUserRepo,
) : IGetUser {
	override fun run(input: GetUserInput): GetUserOutput {
		// Find user
		val user = authenticator.authenticate(input.token)
		if (user == null) {
			throw IllegalArgumentException("User not found")
		}
		return GetUserOutput(user = user.toDomain())
	}
}
