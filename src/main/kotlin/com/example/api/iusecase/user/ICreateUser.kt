package com.example.api.iusecase.user

data class CreateUserInputData(
	val name: String,
	val email: String,
	val password: String,
)

data class CreateUserInput(
	val data: CreateUserInputData,
)

data class CreateUserOutput(
	val codeId: String,
)

interface ICreateUser {
	fun run(input: CreateUserInput): CreateUserOutput
}
