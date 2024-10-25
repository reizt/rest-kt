package com.example.api.usecase.user

import com.example.api.domain.UserStatus
import com.example.api.iservice.IHasher
import com.example.api.iservice.IMailer
import com.example.api.iservice.MailerSendInput
import com.example.api.iservice.repo.ICodeRepo
import com.example.api.iservice.repo.IUserRepo
import com.example.api.iservice.repo.RepoCode
import com.example.api.iservice.repo.RepoUser
import com.example.api.iusecase.user.CreateUserInput
import com.example.api.iusecase.user.CreateUserOutput
import com.example.api.iusecase.user.ICreateUser
import com.example.api.usecase.generateCode
import com.example.api.usecase.generateId
import org.springframework.stereotype.Service

@Service
class CreateUser(
	private val hasher: IHasher,
	private val mailer: IMailer,
	private val userRepo: IUserRepo,
	private val codeRepo: ICodeRepo,
) : ICreateUser {
	override fun run(input: CreateUserInput): CreateUserOutput {
		// Check existence
		val existing = userRepo.findByEmail(input.data.email)
		if (existing != null) {
			throw IllegalArgumentException("User already exists")
		}

		// Create user
		val passwordHash = hasher.hash(input.data.password)
		val user =
			RepoUser(
				id = generateId(),
				name = input.data.name,
				email = input.data.email,
				status = UserStatus.ACTIVE,
				passwordHash = passwordHash,
			)
		userRepo.create(user)

		// Generate code
		val codeValue = generateCode()
		val codeValueHash = hasher.hash(codeValue)
		val code =
			RepoCode(
				id = generateId(),
				email = input.data.email,
				valueHash = codeValueHash,
			)
		codeRepo.create(code)

		// Send code
		val mailerSendInput =
			MailerSendInput(
				to = input.data.email,
				subject = "Verify your email",
				text = "Please verify your email by clicking the link below",
				html =
					"""
					<p>Please verify your email by clicking the link below:</p>
					<a href="https://example.com/verify-code?codeId=${code.id}&code=$codeValue">Verify your email</a>
					""".trimIndent(),
			)
		mailer.send(mailerSendInput)

		return CreateUserOutput(codeId = code.id)
	}
}
