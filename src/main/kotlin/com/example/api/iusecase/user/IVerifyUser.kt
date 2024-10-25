package com.example.api.iusecase.user

data class VerifyUserInput(
	val codeId: String,
	val code: String,
)

data class VerifyUserOutput(
	val token: String,
)

interface IVerifyUser {
	fun run(input: VerifyUserInput): VerifyUserOutput
}
