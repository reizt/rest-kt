package com.example.api.usecase.user

import com.example.api.iservice.IHasher
import com.example.api.iservice.ISigner
import com.example.api.iservice.repo.IUserRepo
import com.example.api.iusecase.user.ILogin
import com.example.api.iusecase.user.LoginInput
import com.example.api.iusecase.user.LoginOutput
import com.google.gson.Gson
import org.springframework.stereotype.Service

@Service
class Login(
	private val signer: ISigner,
	private val hasher: IHasher,
	private val userRepo: IUserRepo,
) : ILogin {
	override fun run(input: LoginInput): LoginOutput {
		// Find user
		val user = userRepo.findByEmail(input.email)
		if (user == null) {
			throw IllegalArgumentException("User not found")
		}

		// Check password
		val isCorrectPassword = hasher.compare(user.passwordHash, input.password)
		if (!isCorrectPassword) {
			throw IllegalArgumentException("Incorrect password")
		}

		// Issue login token
		val tokenPayloadJson = user.toDomain()
		val tokenPayload = Gson().toJson(tokenPayloadJson)
		val expiresAt = System.currentTimeMillis() + 60 * 60 * 1000
		val token = signer.sign(tokenPayload, expiresAt)

		return LoginOutput(token = token)
	}
}
