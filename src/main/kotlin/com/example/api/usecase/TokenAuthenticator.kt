package com.example.api.usecase

import com.example.api.iservice.ISigner
import com.example.api.iservice.repo.IUserRepo
import com.example.api.iservice.repo.RepoUser
import org.springframework.stereotype.Component

@Component
class TokenAuthenticator(
	private val signer: ISigner,
	private val userRepo: IUserRepo,
	private val tokenPayloadProvider: TokenPayloadProvider,
) {
	fun authenticate(token: String): RepoUser? {
		val tokenPayloadJson = signer.verify(token)
		val tokenPayload = tokenPayloadProvider.deserialize(tokenPayloadJson)

		val user = userRepo.findById(tokenPayload.userId)
		return user
	}
}
