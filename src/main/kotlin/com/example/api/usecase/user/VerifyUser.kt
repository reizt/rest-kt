package com.example.api.usecase.user

import com.example.api.domain.UserStatus
import com.example.api.iservice.IHasher
import com.example.api.iservice.ISigner
import com.example.api.iservice.repo.ICodeRepo
import com.example.api.iservice.repo.IUserRepo
import com.example.api.iservice.repo.RepoUserUpdate
import com.example.api.iusecase.user.IVerifyUser
import com.example.api.iusecase.user.VerifyUserInput
import com.example.api.iusecase.user.VerifyUserOutput
import com.example.api.usecase.TokenPayload
import com.example.api.usecase.TokenPayloadProvider
import org.springframework.stereotype.Service

@Service
class VerifyUser(
	private val signer: ISigner,
	private val hasher: IHasher,
	private val userRepo: IUserRepo,
	private val codeRepo: ICodeRepo,
	private val tokenPayloadProvider: TokenPayloadProvider,
) : IVerifyUser {
	override fun run(input: VerifyUserInput): VerifyUserOutput {
		// Find code
		val code = codeRepo.findById(input.codeId)
		if (code == null) {
			throw IllegalArgumentException("Code not found")
		}

		// Check code
		val isCorrectCode = hasher.compare(code.valueHash, input.code)
		if (!isCorrectCode) {
			throw IllegalArgumentException("Code is incorrect")
		}

		// Find user
		val user = userRepo.findByEmail(code.email)
		if (user == null) {
			throw IllegalArgumentException("User not found")
		}

		// Update user status
		val updateData =
			RepoUserUpdate(
				name = user.name,
				email = user.email,
				status = UserStatus.ACTIVE,
				passwordHash = user.passwordHash,
			)
		userRepo.update(user.id, updateData)

		// Issue login token
		val tokenPayloadJson = tokenPayloadProvider.serialize(TokenPayload(user.id))
		val expiresAt: Long = System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7 // 7 days
		val token = signer.sign(tokenPayloadJson, expiresAt)

		return VerifyUserOutput(token)
	}
}
