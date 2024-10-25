package com.example.api.iusecase.user

data class LoginInput(
	val email: String,
	val password: String,
)

data class LoginOutput(
	val token: String,
)

interface ILogin {
	fun run(input: LoginInput): LoginOutput
}
